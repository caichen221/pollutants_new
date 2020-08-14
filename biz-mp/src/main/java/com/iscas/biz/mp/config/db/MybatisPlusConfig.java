package com.iscas.biz.mp.config.db;


import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@MapperScan({"com.iscas.biz.test.mapper*","com.iscas.base.mp.table.mapper*",
//        "com.iscas.base.mp.test.mapper*","com.iscas.biz.mp.mapper*"})//这个注解，作用相当于下面的@Bean MapperScannerConfigurer，2者配置1份即可
public  class MybatisPlusConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * mybatis-plus分页插件<br>     * 文档：http://mp.baomidou.com<br>
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        return paginationInterceptor;
//    }

    /**
     * 逻辑删除配置
     * 3.1.1以上版本无需配置
    * */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

//    /**
//     * SQL执行效率插件
//     */
//    @Bean
//    @Profile({"dev","test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setFormat(true);
////        performanceInterceptor.setMaxTime(500);
////        performanceInterceptor.setWriteInLog(true);
//        return performanceInterceptor;
//    }

    /*乐观锁插件*/
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.baomidou.springboot.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        String mpScannerPackage = environment.getProperty("mp.scanner.package");
        scannerConfigurer.setBasePackage(mpScannerPackage);
        return scannerConfigurer;
    }


}
