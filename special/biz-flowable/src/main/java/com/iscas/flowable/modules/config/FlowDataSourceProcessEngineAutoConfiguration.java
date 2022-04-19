//package com.iscas.flowable.modules.config;
//
//import org.flowable.common.engine.impl.cfg.IdGenerator;
//import org.flowable.engine.ProcessEngineConfiguration;
//import org.flowable.idm.engine.IdmEngineConfiguration;
//import org.flowable.idm.engine.configurator.IdmEngineConfigurator;
//import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
//import org.flowable.spring.SpringProcessEngineConfiguration;
//import org.flowable.spring.boot.FlowableMailProperties;
//import org.flowable.spring.boot.FlowableProperties;
//import org.flowable.spring.boot.ProcessEngineAutoConfiguration;
//import org.flowable.spring.boot.app.FlowableAppProperties;
//import org.flowable.spring.boot.idm.FlowableIdmProperties;
//import org.flowable.spring.boot.process.FlowableProcessProperties;
//import org.flowable.spring.boot.process.Process;
//import org.flowable.spring.boot.process.ProcessAsync;
//import org.flowable.spring.boot.process.ProcessAsyncHistory;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
///**
// * @author zhuquanwen
// * @version 1.0
// * @date 2022/4/18 21:14
// * @since jdk11
// */
//@SuppressWarnings("unused")
//@Configuration
//@EnableConfigurationProperties(FlowableProperties.class)
//public class FlowDataSourceProcessEngineAutoConfiguration extends ProcessEngineAutoConfiguration {
//    @Value("${activiti.diagram.activityFontName}")
//    private String activityFontName;
//
//    @Value("${activiti.diagram.labelFontName}")
//    private String labelFontName;
//
//    @Value("${activiti.diagram.annotationFontName}")
//    private String annotationFontName;
//
//    /**
//     * https://gitee.com/baseweb/JSite/issues/IV4T7
//     * 关闭flowable异步执行器
//     */
//    @Value("${activiti.isSynActivitiIndetity}")
//    private boolean isSynActivitiIndetity;
//
//    public FlowDataSourceProcessEngineAutoConfiguration(FlowableProperties flowableProperties,
//                                                        FlowableProcessProperties processProperties, FlowableAppProperties appProperties, FlowableIdmProperties idmProperties,
//                                                        FlowableMailProperties mailProperties) {
//        super(flowableProperties, processProperties, appProperties, idmProperties, mailProperties);
//    }
//
//    @Override
//    @Bean
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
//                                                                             @Process ObjectProvider<IdGenerator> processIdGenerator,
//                                                                             ObjectProvider<IdGenerator> globalIdGenerator,
//                                                                             @ProcessAsync ObjectProvider<AsyncExecutor> asyncExecutorProvider,
//                                                                             @ProcessAsyncHistory ObjectProvider<AsyncExecutor> asyncHistoryExecutorProvider) throws IOException {
//
//        SpringProcessEngineConfiguration conf = super.springProcessEngineConfiguration(dataSource, platformTransactionManager, processIdGenerator, globalIdGenerator, asyncExecutorProvider, asyncHistoryExecutorProvider);
//        conf.setIdmEngineConfigurator(idmEngineConfigurator(dataSource));
//        conf.setActivityFontName(activityFontName);
//        conf.setLabelFontName(labelFontName);
//        conf.setAnnotationFontName(annotationFontName);
//        conf.setAsyncExecutorActivate(isSynActivitiIndetity);
//        conf.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
//        return conf;
//    }
//
//    @Bean
//    public IdmEngineConfigurator idmEngineConfigurator(DataSource dataSource) {
//        IdmEngineConfiguration idmEngineConfiguration = new IdmEngineConfiguration();
//        idmEngineConfiguration.setDataSource(dataSource);
//        idmEngineConfiguration.setIdmIdentityService(new FlowIdentityServiceImpl());
//
//        IdmEngineConfigurator idmEngineConfigurator = new IdmEngineConfigurator();
//        idmEngineConfigurator.setIdmEngineConfiguration(idmEngineConfiguration);
//        return idmEngineConfigurator;
//    }
//}
