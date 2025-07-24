package com.example.backend.domain.global.config;

import com.example.backend.domain.global.router.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${custom.datasource.write.jdbc-url}")
    private String writeUrl;
    @Value("${custom.datasource.write.username}")
    private String writeUser;
    @Value("${custom.datasource.write.password}")
    private String writePass;

    @Value("${custom.datasource.read.jdbc-url}")
    private String readUrl;
    @Value("${custom.datasource.read.username}")
    private String readUser;
    @Value("${custom.datasource.read.password}")
    private String readPass;

    @Bean
    public DataSource writeDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(writeUrl);
        dataSource.setUsername(writeUser);
        dataSource.setPassword(writePass);
        return dataSource;
    }

    @Bean
    public DataSource readDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(readUrl);
        dataSource.setUsername(readUser);
        dataSource.setPassword(readPass);
        return dataSource;
    }

    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.WRITE, writeDataSource());
        targetDataSources.put(DataSourceType.READ, readDataSource());

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());
        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
