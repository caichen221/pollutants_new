package com.iscas.biz.config.mp;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.config.db.SqlSessionFactoryCustomizer;
import com.iscas.common.tools.core.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 扩展mybatis-plus的配置
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/1/3 19:58
 * @since jdk1.8
 */
@Slf4j
@Component
@ConditionalOnMybatis
public class MySqlSessionFactoryCustomizer implements SqlSessionFactoryCustomizer {
    @Override
    public void customize(Configuration configuration, FactoryBean sessionFactory) {
        if (sessionFactory instanceof MybatisSqlSessionFactoryBean) {
            MybatisSqlSessionFactoryBean mssfb = (MybatisSqlSessionFactoryBean) sessionFactory;
            try {
                Field globalConfigField = MybatisSqlSessionFactoryBean.class.getDeclaredField("globalConfig");
                ReflectUtils.makeAccessible(globalConfigField);
                GlobalConfig globalConfig = (GlobalConfig) globalConfigField.get(mssfb);
                globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("MySqlSessionFactoryCustomizer 出错", e);
            }
        }
    }
}
