package com.iscas.biz.jpa.config.db.single;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * //TODO
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

    private Map<String, Object> getVenorProperties() {
        https://blog.csdn.net/fall_hat/article/details/105219307

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
