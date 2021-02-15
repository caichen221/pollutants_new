package com.iscas.sso.mix.ticket.share.agent;

import com.iscas.common.rpc.tools.grpc.server.GrpcServerUtils;
import com.iscas.sso.mix.ticket.share.agent.handler.TicketHandler;
import com.iscas.sso.mix.ticket.share.agent.service.TicketCommonService;
import com.iscas.sso.mix.ticket.share.agent.service.TicketServiceImpl;
import com.iscas.sso.mix.ticket.share.agent.util.ConfigUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.fu.jafu.BeanDefinitionDsl;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.fu.jafu.webmvc.WebMvcServerDsl;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

import static org.springframework.fu.jafu.Jafu.webApplication;
import static org.springframework.fu.jafu.webmvc.WebMvcServerDsl.webMvc;

/**
 * 启动类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/9 11:13
 * @since jdk1.8
 */
@Slf4j
public class Main {
    // 配置
    public static JafuApplication app = webApplication(applicationDsl -> applicationDsl.beans(beanDefinitionDsl -> initBeans(beanDefinitionDsl))
            .enable(webMvc(serverDsl -> enableMvc(serverDsl))));

    // 启用mvc
    public static ApplicationContextInitializer<GenericApplicationContext> enableMvc(WebMvcServerDsl serverDsl) {
        // 设置端口号
        try {
            //只允许本地访问
            Field serverPropertiesField = serverDsl.getClass().getDeclaredField("serverProperties");
            serverPropertiesField.setAccessible(true);
            ServerProperties serverProperties = (ServerProperties) serverPropertiesField.get(serverDsl);
            serverProperties.setAddress(InetAddress.getByName("127.0.0.1"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return serverDsl.undertow().port(Integer.parseInt(ConfigUtils.readProp("ticket.share.http.server.port")))
                .router(router -> initRouter(serverDsl, router)).converters(c -> initConverters(serverDsl));
    }

    // 设置格式转换
    public static WebMvcServerDsl initConverters(WebMvcServerDsl serverDsl) {
        return serverDsl.converters(c -> c.string().jackson());
    }

    // 设置接口地址路由
    public static WebMvcServerDsl initRouter(WebMvcServerDsl serverDsl, RouterFunctions.Builder router) {
        TicketHandler handler = serverDsl.ref(TicketHandler.class);
        router.PUT("/storeTicket", handler::storeTicket);
        router.GET("/getTicket", handler::getTicket);
        router.onError(Throwable.class, (e, request) -> {
            log.error("出现错误", e);
            ResponseEntity responseEntity = new ResponseEntity();
            if (e instanceof BaseException) {
                responseEntity.setDesc(((BaseException) e).getMsgDetail());
                responseEntity.setMessage(e.getMessage());
            } else {
                responseEntity.setMessage("服务器内部错误");
                responseEntity.setDesc(e.getMessage());
            }
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseEntity);
        });
        return serverDsl;
    }

    // 声明bean
    public static BeanDefinitionDsl initBeans(BeanDefinitionDsl beanDefinitionDsl) {
        beanDefinitionDsl
                .bean(TicketHandler.class)
                .bean(TicketCommonService.class);
//                .bean(TestService.class)
//                .bean(TestService2.class);
        return beanDefinitionDsl;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        app.run(args);
        log.info("========HTTP服务已发布===========");
        TicketServiceImpl ticketService = new TicketServiceImpl();
        Server server = GrpcServerUtils.start(ticketService, Integer.parseInt(ConfigUtils.readProp("ticket.share.grpc.server.port")),
                new ServerTransportFilter() {
                    @Override
                    public Attributes transportReady(Attributes transportAttrs) {
                        Map data = null;
                        try {
                            Field dataField = transportAttrs.getClass().getDeclaredField("data");
                            dataField.setAccessible(true);
                            data = (Map) dataField.get(transportAttrs);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        if (data != null) {
                            for (Object o : data.entrySet()) {
                                if (Objects.equals("remote-addr", ((Map.Entry) o).getKey().toString())) {
                                    InetSocketAddress inetSocketAddress = (InetSocketAddress) ((Map.Entry) o).getValue();
                                    InetAddress address = inetSocketAddress.getAddress();
                                    String hostAddress = address.getHostAddress();
                                    if (!Objects.equals("127.0.0.1", hostAddress)) {
                                        throw new RuntimeException("不允许的远程IP，只允许127.0.0.1");
                                    }
                                }
                            }
                        }

                        return super.transportReady(transportAttrs);
                    }

                    @Override
                    public void transportTerminated(Attributes transportAttrs) {
                        super.transportTerminated(transportAttrs);
                    }
                });
        log.info("========ticket共享服务已启动===========");
        server.awaitTermination();
    }

}