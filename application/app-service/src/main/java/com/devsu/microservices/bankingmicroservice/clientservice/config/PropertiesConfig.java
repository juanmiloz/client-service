package com.devsu.microservices.bankingmicroservice.clientservice.config;

import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.config.PostgresqlConnectionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PostgresqlConnectionProperties.class)
public class PropertiesConfig {
}
