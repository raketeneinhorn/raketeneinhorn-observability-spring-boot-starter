package com.raketeneinhorn.observability.spring.boot.starter.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ObservabilityConfigurationProperties.class)
public class ObservabilityConfiguration {
}
