package com.raketeneinhorn.observability.spring.boot.starter.environment;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.structured.CommonStructuredLogFormat;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Order
public class ObservabilityEnvironmentPostProcessor implements EnvironmentPostProcessor {

    protected static final String BANNER_MODE_PROPERTY_KEY = "spring.main.banner-mode";
    protected static final String TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY = "management.tracing.sampling.probability";
    protected static final String LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY = "logging.structured.format.console";

    List<EnvironmentSpec<?>> environmentSpecs = List.of(
        new EnvironmentSpec<>(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class, Banner.Mode.OFF, e -> true),
        new EnvironmentSpec<>(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class, 1.0F, e -> true),
        new EnvironmentSpec<>(LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY, CommonStructuredLogFormat.class, CommonStructuredLogFormat.LOGSTASH, not(this::isLocalEnvironment))

    );

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> mapSource = new HashMap<>();

        environmentSpecs.stream()
            .filter(es -> es.includeForEnvironment().test(environment))
            .peek(es -> System.out.println("applying " + es))
            .forEach(es -> mapSource.put(
                es.key(),
                es.effiectiveValueForEnvironment(environment)
            ));

        PropertySource<Map<String,Object>> mapPropertySource = new MapPropertySource(
            ObservabilityEnvironmentPostProcessor.class.getName(),
            mapSource
        );
        environment.getPropertySources().addLast(mapPropertySource);
    }

    private boolean isLocalEnvironment(Environment environment) {
        return Arrays.stream(environment.getActiveProfiles()).anyMatch("local"::equalsIgnoreCase);
    }

    private record EnvironmentSpec<T>(
        String key,
        Class<T> targetType,
        T defaultValue,
        Predicate<Environment> includeForEnvironment
    ) {

        private T effiectiveValueForEnvironment(Environment environment) {
            return environment.getProperty(key, targetType, defaultValue);
        }

    }

}
