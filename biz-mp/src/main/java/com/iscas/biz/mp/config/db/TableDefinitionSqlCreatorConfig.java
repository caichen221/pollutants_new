package com.iscas.biz.mp.config.db;

import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.biz.mp.table.service.MysqlTableDefinitionSqlCreatorService;
import com.iscas.biz.mp.table.service.OracleTableDefinitionSqlCreatorService;
import com.iscas.biz.mp.table.service.OscarTableDefinitionSqlCreatorService;
import com.iscas.biz.mp.table.service.interfaces.ITableDefinitionSqlCreatorService;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.text.MessageFormat;

/**
 * 配置TableDefinition
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/12/2 13:50
 * @since jdk1.8
 */
@ConditionalOnMybatis
@Configuration
public class TableDefinitionSqlCreatorConfig implements EnvironmentAware {
    private Environment environment;

    public static String mode = "mysql";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ITableDefinitionSqlCreatorService tableDefinitionSqlCreatorService() {
        String datasourceNames = environment.getProperty("spring.datasource.names");
        //取第一个数据源
        String db1Name = datasourceNames.split(",")[0];
        String driverClassNameKey = MessageFormat.format("spring.datasource.druid.{0}.driver-class-name", db1Name);
        String driverClassName = environment.getProperty(driverClassNameKey);

        //暂时按照driverClassName判断
        if (driverClassName.contains(".mysql.")) {
            mode = "mysql";
            return new MysqlTableDefinitionSqlCreatorService();
        } else if (driverClassName.contains(".oscar.")) {
            mode = "oscar";
            return new OscarTableDefinitionSqlCreatorService();
        } else if (driverClassName.contains(".OracleDriver")) {
            mode = "oracle";
            return new OracleTableDefinitionSqlCreatorService();
        } else {
            //暂时默认都以为跟mysql语句一样吧
            return new MysqlTableDefinitionSqlCreatorService();
        }
    }
}
