package com.springproject.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JPAConfiguration implements InitializingBean {

    private String[] packagesToScan;
    private LocalContainerEntityManagerFactoryBean em;

    @Bean
    public EntityManagerFactory entityManagerFactory(@Autowired DataSource dataSource) {
        return localContainerEntityManagerFactory(dataSource).getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactory(DataSource dataSource) {
        if (em == null) {
            List<String> packages = new ArrayList<>(Arrays.asList(packagesToScan));
            packages.add(Jsr310JpaConverters.class.getPackage().getName()); // jsr 310 jdk8 date support

            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan(packages.toArray(new String[0]));
            em.setJpaVendorAdapter(vendorAdapter);
            em.setJpaProperties(additionalProperties());
            em.afterPropertiesSet();
        }
        return em;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.implicit_naming_strategy", "jpa");
        properties.setProperty("hibernate.transaction.jta.platform", "org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("format_sql", "false");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.order_inserts", "true");
        return properties;
    }

    public void setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(packagesToScan, "entity class packages to scan cannot be null");
    }

}
