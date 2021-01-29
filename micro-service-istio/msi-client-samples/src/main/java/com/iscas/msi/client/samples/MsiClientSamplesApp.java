package com.iscas.msi.client.samples;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * 启动类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/01/29 13:58
 * @since jdk1.8
 */
@Slf4j
@SpringBootApplication
public class MsiClientSamplesApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MsiClientSamplesApp.class);
//        springApplication.addListeners(new MyApplicationBeforeStartListener(), new MyApplicationStartedListener());
        springApplication.run(args);
        log.info("==========服务已启动=================");
    }
    /**
     *重写configure
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        builder.listeners(new MyApplicationBeforeStartListener(), new MyApplicationStartedListener());
        SpringApplicationBuilder sources = builder.sources(MsiClientSamplesApp.class);
        log.info("==========服务已启动=================");
        return sources;
    }

}
