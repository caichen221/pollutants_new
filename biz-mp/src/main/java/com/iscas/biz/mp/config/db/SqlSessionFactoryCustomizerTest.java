package com.iscas.biz.mp.config.db;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/5 12:03
 * @since jdk1.8
 */
@Component
public class SqlSessionFactoryCustomizerTest implements SqlSessionFactoryCustomizer {
    @Override
    public void customize(Configuration configuration, FactoryBean sessionFactory) {
        if (sessionFactory instanceof MybatisSqlSessionFactoryBean){
            MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = (MybatisSqlSessionFactoryBean) sessionFactory;
            //自定义插件
            //mybatisSqlSessionFactoryBean.setPlugins(new PageInterceptor(),new OptimisticLockerInterceptor());
        }
        //自定义插件
        //configuration.addInterceptor(new PageInterceptor());
    }
}
