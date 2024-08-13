package com.raketeneinhorn.observability.spring.boot.starter.exception.configuration;

import com.raketeneinhorn.observability.spring.boot.starter.exception.SimpleResponseEntityExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SimpleResponseEntityExceptionHandler.class)
public class ObservabilityExceptionConfiguration {
}
