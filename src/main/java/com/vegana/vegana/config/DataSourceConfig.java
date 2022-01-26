package com.vegana.vegana.config;

import com.vegana.vegana.jdbc.datasource.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Qualifier("readDataSource")
    @ConfigurationProperties(prefix = "datasource.read")
    public DataSource readDataSource() {

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("writeDataSource")
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("routingDataSource")
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                        @Qualifier("readDataSource") DataSource readDataSource ) {

        return ReplicationRoutingDataSource.of(writeDataSource, readDataSource);
    }

    @Bean
    @Qualifier("dataSource")
    @Primary
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
