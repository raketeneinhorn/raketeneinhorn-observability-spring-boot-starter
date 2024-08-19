package com.raketeneinhorn.observability.spring.boot.starter.environment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.StandardEnvironment;

import static com.raketeneinhorn.observability.spring.boot.starter.environment.ObservabilityEnvironmentPostProcessor.BANNER_MODE_PROPERTY_KEY;
import static com.raketeneinhorn.observability.spring.boot.starter.environment.ObservabilityEnvironmentPostProcessor.TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ObservabilityEnvironmentPostProcessorTest {

    private StandardEnvironment standardEnvironment;
    private SpringApplication springApplication;

    private ObservabilityEnvironmentPostProcessor observabilityEnvironmentPostProcessorUnderTest;

    @BeforeEach
    void setUp() {
        standardEnvironment = new StandardEnvironment();
        springApplication = Mockito.mock();

        observabilityEnvironmentPostProcessorUnderTest = new ObservabilityEnvironmentPostProcessor();
    }

    @Nested
    class PostProcessEnvironment {

        @Test
        void disablesSpringBootBanner() {
            assertThat(standardEnvironment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class)).isNull();

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(standardEnvironment, springApplication);
            assertThat(standardEnvironment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class)).isEqualTo(Banner.Mode.OFF);
        }

        @Test
        void configuresTracingSamplingPropability() {
            assertThat(standardEnvironment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class)).isNull();

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(standardEnvironment, springApplication);
            assertThat(standardEnvironment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class)).isEqualTo(0.1F);
        }
    }

}
