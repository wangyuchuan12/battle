package com.wyc.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.wyc.common.repositories","com.battle.dao"})
@EnableTransactionManagement
@ImportResource("classpath:applicationContext.xml")
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = new Properties();
        File databaseConfigFile = new File("/etc/battle/database.properties");
        InputStream defaultConfig = this.getClass().getResourceAsStream("/database.properties");

        if (databaseConfigFile.exists()) {
            properties.load(new FileInputStream(databaseConfigFile));
        } else {
            properties.load(defaultConfig);
        }
    
        String databaseUrl = properties.getProperty("database.url");
        String databaseUsername = properties.getProperty("database.username");
        String databasePassword = properties.getProperty("database.password");

        final DruidDataSource ds = new DruidDataSource();
        ds.setUrl(databaseUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);

        ds.setInitialSize(5);
        ds.setMinIdle(5);
        ds.setMaxActive(54);
        ds.setMaxWait(60000);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("SELECT 1");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setPoolPreparedStatements(false);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);

        return ds;
    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(false);
        vendorAdapter
                .setDatabasePlatform("com.wyc.common.extend.MySqlDialectExtend");
        vendorAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.wyc.common.domain","com.wyc.common.wx.domain","com.battle.domain");
        factory.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.jdbc.batch_size", "50");
        properties.setProperty("hibernate.jdbc.fetch_size", "50");
      
        properties.setProperty("hibernate.generate_statistics", "true");
        properties.setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");
        properties.setProperty("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
        properties.setProperty("hibernate.cache.use_query_cache", "true");
        properties.setProperty("ernate.cache.use_second_level_cache", "true");
       
        properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();
        return factory.getObject();
        
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}

