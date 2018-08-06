package com.epam.core.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The type Jpa config.
 */
@Configuration
@Import(EntityBeanHolder.class)
@ComponentScan(basePackages = {"com.epam.core.config.datasource", "com.epam.core.strategy.jpa", "com.epam.core.entity", "com.epam.core.service", "com.epam.core.util", "com.epam.core.log"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories("com.epam.core.entity")
public class JpaConfig {

    /**
     * Factory bean local container entity manager factory bean.
     *
     * @param source  the source
     * @param adapter the adapter
     * @return the local container entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean(DataSource source, JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(source);
        entityManagerFactoryBean.setJpaVendorAdapter(adapter);
        entityManagerFactoryBean.setPackagesToScan("com.epam.core.entity");
        return entityManagerFactoryBean;
    }

    /**
     * Transaction manager platform transaction manager.
     *
     * @param dataSource the data source
     * @param adapter    the adapter
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource, JpaVendorAdapter adapter) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(factoryBean(dataSource, adapter).getObject());
        return manager;
    }

    /**
     * Jpa test vendor adapter jpa vendor adapter.
     *
     * @return the jpa vendor adapter
     */
    @Profile("test")
    @Bean
    public JpaVendorAdapter jpaTestVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        adapter.setDatabase(Database.H2);
        return adapter;
    }

    /**
     * Jpa dev vendor adapter jpa vendor adapter.
     *
     * @return the jpa vendor adapter
     */
    @Profile("dev")
    @Bean
    public JpaVendorAdapter jpaDevVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(true);
        return adapter;
    }
}
