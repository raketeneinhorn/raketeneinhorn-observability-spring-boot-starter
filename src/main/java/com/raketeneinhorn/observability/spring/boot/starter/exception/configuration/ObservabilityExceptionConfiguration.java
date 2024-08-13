package com.raketeneinhorn.observability.spring.boot.starter.exception.configuration;

import com.raketeneinhorn.observability.spring.boot.starter.exception.ResponseEntityExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ResponseEntityExceptionHandler.class)
public class ObservabilityExceptionConfiguration {
}
