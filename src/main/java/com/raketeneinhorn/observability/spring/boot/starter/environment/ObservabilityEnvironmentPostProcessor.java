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

    protected static final String BANNER_MODE_PROPERTY_KEY = "spring.main.banner-mode";
    private static final Banner.Mode BANNER_MODE_DEFAULT_VALUE = Banner.Mode.OFF;

    protected static final String TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY = "management.tracing.sampling.probability";
    private static final Float TRACING_SAMPLING_PROBABILITY_DEFAULT_VALUE = 1.0F;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Banner.Mode bannerMode = environment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class, BANNER_MODE_DEFAULT_VALUE);
        Float tracingSamplingProbability = environment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class, TRACING_SAMPLING_PROBABILITY_DEFAULT_VALUE);

        PropertySource<Map<String,Object>> mapPropertySource = new MapPropertySource(
            ObservabilityEnvironmentPostProcessor.class.getName(),
            Map.of(
                BANNER_MODE_PROPERTY_KEY, bannerMode,
                TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, tracingSamplingProbability
            )
        );
        environment.getPropertySources().addLast(mapPropertySource);
    }

}
