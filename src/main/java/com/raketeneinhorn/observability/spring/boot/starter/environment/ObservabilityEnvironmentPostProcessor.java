package com.raketeneinhorn.observability.spring.boot.starter.environment;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;

@Order
public class ObservabilityEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final static String BANNER_MODE_PROPERTY_KEY = "spring.main.banner-mode";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PropertySource<Map<String,Object>> mapPropertySource = new MapPropertySource(
            ObservabilityEnvironmentPostProcessor.class.getName(),
            Map.of(BANNER_MODE_PROPERTY_KEY, Banner.Mode.OFF)
        );
        environment.getPropertySources().addLast(mapPropertySource);
    }

}
