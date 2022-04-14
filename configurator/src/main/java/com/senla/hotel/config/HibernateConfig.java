package com.senla.hotel.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class HibernateConfig {
    @Value("${hibernate.package.to.scan}")
    private String packageToScan;

    @Value("${hibernate.datasource.driver.class.name}")
    private String driverClass;

    @Value("${hibernate.datasource.url}")
    private String url;

    @Value("${hibernate.datasource.username}")
    private String username;

    @Value("${hibernate.datasource.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show.sql}")
    private String showSql;

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", showSql);

        bean.setHibernateProperties(hibernateProperties);
        bean.setDataSource(dataSource());
        bean.setPackagesToScan(packageToScan);
        return bean;
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setAcquireIncrement(10);
        dataSource.setIdleConnectionTestPeriod(0);
        dataSource.setInitialPoolSize(5);
        dataSource.setMaxIdleTime(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setMaxStatements(100);
        dataSource.setMinPoolSize(5);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());
        return template;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws PropertyVetoException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
