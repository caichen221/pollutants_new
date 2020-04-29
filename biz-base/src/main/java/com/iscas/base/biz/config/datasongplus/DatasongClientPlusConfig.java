package com.iscas.base.biz.config.datasongplus;

import com.iscas.datasong.client.plus.config.DetBeanPostProcessor;
import com.iscas.datasong.client.plus.model.DetProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2018/10/12 14:58
 * @since jdk1.8
 */
@Slf4j
//@Configuration
@EnableConfigurationProperties(DatasongPlusProps.class)
//@ConditionalOnClass(DatasongPlusProps.class)
@ConditionalOnProperty(prefix = "datasong.client.plus",matchIfMissing = true,value = "enabled")
public class DatasongClientPlusConfig {
    @Value("${Global.DbServer}")
    private String dbServer;
    @Value("${Global.DbName}")
    private String dbName;
    @Autowired
    private DatasongPlusProps datasongPlusProps;

    @Bean
    public DetBeanPostProcessor detBeanPostProcessor(){
        log.info("------------配置datasong-client-plus----------------");

        String dbName = this.dbName;
        String ip = "172.16.10.180";
        String port = "15680";
        String s = StringUtils.substringAfter(dbServer, "http://");
        ip = StringUtils.substringBefore(s, ":");
        port = StringUtils.substringAfter(s, ":");

        DetProps detProps = new DetProps();
        detProps.setDbName(dbName);
        detProps.setIp(ip);
        detProps.setPort(port);
        detProps.setProxyType(DetProps.ProxyType.CGLIB);
        detProps.setBasePackages(datasongPlusProps.getPackages());
        log.info("------------配置datasong-client-plus结束----------------");
        return new DetBeanPostProcessor(detProps);
    }
}
