package com.iscas.biz.mp.config.db;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/5 9:12
 * @since jdk1.8
 * 扩展 Configuration、SqlSessionFactory
 */
@FunctionalInterface
public interface SqlSessionFactoryCustomizer<T extends Configuration,S extends FactoryBean<? extends SqlSessionFactory>> {

    void customize(Configuration configuration, FactoryBean<? extends SqlSessionFactory> sessionFactory);
}
