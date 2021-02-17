package com.iscas.sso.mix.auth;

import com.iscas.common.rpc.tools.grpc.server.GrpcServerUtils;
import com.iscas.sso.mix.auth.filter.CustomCrosFilter;
import com.iscas.sso.mix.auth.handler.AuthHandler;
import com.iscas.sso.mix.auth.service.AuthCommonService;
import com.iscas.sso.mix.auth.service.AuthServiceImpl;
import com.iscas.sso.mix.auth.util.ConfigUtils;
import com.iscas.templet.common.ResponseEntity;
import com.iscas.templet.exception.BaseException;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.fu.jafu.BeanDefinitionDsl;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.fu.jafu.webmvc.WebMvcServerDsl;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;

import static org.springframework.fu.jafu.Jafu.webApplication;
import static org.springframework.fu.jafu.webmvc.WebMvcServerDsl.webMvc;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/16 12:46
 * @since jdk1.8
 */
@Slf4j
public class AuthMain {
    // 配置
    public static JafuApplication app = webApplication(applicationDsl -> applicationDsl.beans(beanDefinitionDsl -> initBeans(beanDefinitionDsl))
            .enable(webMvc(serverDsl -> enableMvc(serverDsl))));

    // 启用mvc
    public static ApplicationContextInitializer<GenericApplicationContext> enableMvc(WebMvcServerDsl serverDsl) {
//        // 设置端口号
//        try {
//            //只允许本地访问
//            Field serverPropertiesField = serverDsl.getClass().getDeclaredField("serverProperties");
//            serverPropertiesField.setAccessible(true);
//            ServerProperties serverProperties = (ServerProperties) serverPropertiesField.get(serverDsl);
//            serverProperties.setAddress(InetAddress.getByName("127.0.0.1"));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }

        return serverDsl.undertow().port(Integer.parseInt(ConfigUtils.readProp("auth.http.server.port")))
                .router(router -> initRouter(serverDsl, router)).converters(c -> initConverters(serverDsl));
    }

    // 设置格式转换
    public static WebMvcServerDsl initConverters(WebMvcServerDsl serverDsl) {
        return serverDsl.converters(c -> c.string().jackson());
    }

    // 设置接口地址路由
    public static WebMvcServerDsl initRouter(WebMvcServerDsl serverDsl, RouterFunctions.Builder router) {
        AuthHandler handler = serverDsl.ref(AuthHandler.class);
        router.POST("/login", handler::login);
        router.GET("/verify", handler::verify);
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
                .bean(CustomCrosFilter.class)
                .bean(AuthHandler.class)
                .bean(AuthCommonService.class);
        return beanDefinitionDsl;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        app.run(args);
        log.info("========HTTP服务已发布===========");
        AuthServiceImpl authService = new AuthServiceImpl();
        Server server = GrpcServerUtils.start(authService, Integer.parseInt(ConfigUtils.readProp("auth.grpc.server.port")));
        log.info("========ticket共享服务已启动===========");
        server.awaitTermination();
    }
}
