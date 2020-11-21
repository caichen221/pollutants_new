package com.iscas.biz.jpa.config.db.single;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/15 8:52
 * @since jdk1.8
 */

@SuppressWarnings("ALL")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"${entity.location}"}) //设置Repository所在位置
public class JpaConfig {
    private final ObjectProvider<SchemaManagementProvider> providers;

    private final HibernateProperties hibernateProperties;

    private final JpaProperties properties;

    private final ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy;

    private final ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy;

    private final ConfigurableListableBeanFactory beanFactory;

    private final ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;


    @Resource
    private DataSource dataSource;

    @Value("${entity.location}")
    private String entityLocation;

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactorySecond(builder).getObject().createEntityManager();
    }

    @Resource
    private JpaProperties jpaProperties;

    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    @Autowired
    public JpaConfig(ObjectProvider<SchemaManagementProvider> providers, HibernateProperties hibernateProperties, JpaProperties properties, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ConfigurableListableBeanFactory beanFactory, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.providers = providers;
        this.hibernateProperties = hibernateProperties;
        this.properties = properties;
        this.physicalNamingStrategy = physicalNamingStrategy;
        this.implicitNamingStrategy = implicitNamingStrategy;
        this.beanFactory = beanFactory;
        this.hibernatePropertiesCustomizers = hibernatePropertiesCustomizers;
    }


    private Map<String, Object> getVenorProperties() {
//        https://blog.csdn.net/fall_hat/article/details/105219307

//        return jpaProperties.getProperties();

        List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers = determineHibernatePropertiesCustomizers(
                physicalNamingStrategy.getIfAvailable(),
                implicitNamingStrategy.getIfAvailable(), beanFactory,
                this.hibernatePropertiesCustomizers.orderedStream()
                        .collect(Collectors.toList()));
        Supplier<String> defaultDdlMode = () -> new HibernateDefaultDdlAutoProvider(providers)
                .getDefaultDdlAuto(dataSource);
        return new LinkedHashMap<>(this.hibernateProperties.determineHibernateProperties(
                properties.getProperties(),
                new HibernateSettings().ddlAuto(defaultDdlMode)
                        .hibernatePropertiesCustomizers(
                                hibernatePropertiesCustomizers)));

//        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }

    /**
     * 命名策略自动判断
     *
     * @param physicalNamingStrategy
     * @param implicitNamingStrategy
     * @param beanFactory
     * @param hibernatePropertiesCustomizers
     * @return
     */
    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(
            PhysicalNamingStrategy physicalNamingStrategy,
            ImplicitNamingStrategy implicitNamingStrategy,
            ConfigurableListableBeanFactory beanFactory,
            List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        if (ClassUtils.isPresent(
                "org.hibernate.resource.beans.container.spi.BeanContainer",
                getClass().getClassLoader())) {
            customizers
                    .add((properties) -> properties.put(AvailableSettings.BEAN_CONTAINER,
                            new SpringBeanContainer(beanFactory)));
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers.add(new NamingStrategiesHibernatePropertiesCustomizer(
                    physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }
    /**
     * 自动进行建表操作
     */
    class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {

        private final Iterable<SchemaManagementProvider> providers;

        HibernateDefaultDdlAutoProvider(Iterable<SchemaManagementProvider> providers) {
            this.providers = providers;
        }

        public String getDefaultDdlAuto(DataSource dataSource) {
            if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
                return "none";
            }
            SchemaManagement schemaManagement = getSchemaManagement(dataSource);
            if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                return "none";
            }
            return "create-drop";

        }

        @Override
        public SchemaManagement getSchemaManagement(DataSource dataSource) {
            return StreamSupport.stream(this.providers.spliterator(), false)
                    .map((provider) -> provider.getSchemaManagement(dataSource))
                    .filter(SchemaManagement.MANAGED::equals).findFirst()
                    .orElse(SchemaManagement.UNMANAGED);
        }

    }
    private static class NamingStrategiesHibernatePropertiesCustomizer
            implements HibernatePropertiesCustomizer {

        private final PhysicalNamingStrategy physicalNamingStrategy;

        private final ImplicitNamingStrategy implicitNamingStrategy;

        NamingStrategiesHibernatePropertiesCustomizer(
                PhysicalNamingStrategy physicalNamingStrategy,
                ImplicitNamingStrategy implicitNamingStrategy) {
            this.physicalNamingStrategy = physicalNamingStrategy;
            this.implicitNamingStrategy = implicitNamingStrategy;
        }

        /**
         * 数据库命名映射策略
         *
         * @param hibernateProperties the JPA vendor properties to customize
         */
        @Override
        public void customize(Map<String, Object> hibernateProperties) {
            if (this.physicalNamingStrategy != null) {
                hibernateProperties.put("hibernate.physical_naming_strategy",
                        this.physicalNamingStrategy);
            }
            if (this.implicitNamingStrategy != null) {
                hibernateProperties.put("hibernate.implicit_naming_strategy",
                        this.implicitNamingStrategy);
            }
        }
    }

    @Primary
    @Bean(name = "entityManagerFactoryBuilderPrimary")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilderPrimary() {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        return new EntityManagerFactoryBuilder(adapter, getVenorProperties(), persistenceUnitManager);
    }

    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean lcemf = builder
                .dataSource(dataSource)
                .packages(entityLocation) //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .properties(getVenorProperties())
                .build();
        return lcemf;
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerSecond(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecond(builder).getObject());
    }
}
