package com.company.vegana.config;

import com.company.vegana.servlet.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;

@Configuration
public class LoggingConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Filter loggingFilter() {
        return new LoggingFilter();
    }

}
