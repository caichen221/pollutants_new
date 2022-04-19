//package com.iscas.flowable.modules.config;
//
//import liquibase.Liquibase;
//import liquibase.database.Database;
//import liquibase.database.DatabaseConnection;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.resource.ClassLoaderResourceAccessor;
//import lombok.extern.slf4j.Slf4j;
//import org.flowable.idm.api.IdmIdentityService;
//import org.flowable.idm.engine.IdmEngine;
//import org.flowable.idm.engine.IdmEngines;
//import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * @author zhuquanwen
// * @version 1.0
// * @date 2022/4/18 21:07
// * @since jdk11
// */
//@SuppressWarnings("unused")
//@Configuration
//@Slf4j
//public class FlowableBeanConfig {
//
//    protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";
//
//    @Bean
//    public IdmIdentityService idmIdentityService() {
//        IdmEngine idmEngine = IdmEngines.getDefaultIdmEngine();
//        return idmEngine.getIdmIdentityService();
//    }
//
//    @Bean
//    public FlowableModelerAppProperties flowableModelerAppProperties() {
//        return new FlowableModelerAppProperties();
//    }
//
//    @Bean
//    public Liquibase liquibase(DataSource dataSource) {
//        log.info("Configuring Liquibase");
//
//        try {
//            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
//            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
//            database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
//            database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());
//
//            Liquibase liquibase = new Liquibase("META-INF/liquibase/flowable-modeler-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
//            liquibase.update("flowable");
//            return liquibase;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating liquibase database", e);
//        }
//    }
//}
