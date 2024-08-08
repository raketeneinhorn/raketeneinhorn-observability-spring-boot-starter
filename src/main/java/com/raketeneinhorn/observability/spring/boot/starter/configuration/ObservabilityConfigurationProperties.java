package com.raketeneinhorn.observability.spring.boot.starter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.raketeneinhorn.observability.spring.boot.starter.configuration.ObservabilityConfigurationProperties.PREFIX;

@Getter
@Setter
@ConfigurationProperties(prefix = PREFIX)
public class ObservabilityConfigurationProperties {

    public static final String PREFIX = "raketeneinhorn.observability";

}
