package com.iscas.biz.jafu;

import com.iscas.biz.jafu.handler.MyHandler;
import com.iscas.biz.jafu.service.TestService;
import com.iscas.biz.jafu.service.TestService2;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.fu.jafu.BeanDefinitionDsl;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.fu.jafu.webmvc.WebMvcServerDsl;
import org.springframework.web.servlet.function.RouterFunctions;

import static org.springframework.fu.jafu.Jafu.webApplication;
import static org.springframework.fu.jafu.webmvc.WebMvcServerDsl.webMvc;

/**
 * jafu启动类
 *
 * 官方网址：https://spring.io/blog/2018/10/02/the-evolution-of-spring-fu
 *
 * github：https://github.com/spring-projects-experimental/spring-fu
 *
 * 官方示例github：https://github.com/spring-projects-experimental/spring-fu/tree/master/samples
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/5 21:26
 * @since jdk1.8
 */
public class SpringFuApplication {
    // 配置
    public static JafuApplication app = webApplication(applicationDsl -> applicationDsl.beans(beanDefinitionDsl -> initBeans(beanDefinitionDsl))
            .enable(webMvc(serverDsl -> enableMvc(serverDsl))));

    // 启用mvc
    public static ApplicationContextInitializer<GenericApplicationContext> enableMvc(WebMvcServerDsl serverDsl) {
        // 设置端口号
        return serverDsl.port(serverDsl.profiles().contains("test") ? 18181 : 18080)
                .router(router -> initRouter(serverDsl, router)).converters(c -> initConverters(serverDsl));
    }

    // 设置格式转换
    public static WebMvcServerDsl initConverters(WebMvcServerDsl serverDsl) {
        return serverDsl.converters(c -> c.string().jackson());
    }

    // 设置接口地址路由
    public static WebMvcServerDsl initRouter(WebMvcServerDsl serverDsl, RouterFunctions.Builder router) {
        MyHandler handler = serverDsl.ref(MyHandler.class);
        router.GET("/", handler::test);
        return serverDsl;
    }

    // 声明bean
    public static BeanDefinitionDsl initBeans(BeanDefinitionDsl beanDefinitionDsl) {
        beanDefinitionDsl
                .bean(MyHandler.class)
                .bean(TestService.class)
                .bean(TestService2.class);
        return beanDefinitionDsl;
    }

    public static void main (String[] args) {
        app.run(args);
    }



}
