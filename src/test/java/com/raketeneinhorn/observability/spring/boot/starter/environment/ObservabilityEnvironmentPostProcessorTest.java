package com.raketeneinhorn.observability.spring.boot.starter.environment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.structured.CommonStructuredLogFormat;
import org.springframework.mock.env.MockEnvironment;

import static com.raketeneinhorn.observability.spring.boot.starter.environment.ObservabilityEnvironmentPostProcessor.*;
import static org.assertj.core.api.Assertions.assertThat;

class ObservabilityEnvironmentPostProcessorTest {

    private MockEnvironment mockEnvironment;
    private SpringApplication springApplication;

    private ObservabilityEnvironmentPostProcessor observabilityEnvironmentPostProcessorUnderTest;

    @BeforeEach
    void setUp() {
        mockEnvironment = new MockEnvironment();
        springApplication = Mockito.mock();

        observabilityEnvironmentPostProcessorUnderTest = new ObservabilityEnvironmentPostProcessor();
    }

    @Nested
    class PostProcessEnvironment {

        @Test
        void maintainsSpringBootBannerMode() {
            mockEnvironment.setProperty(BANNER_MODE_PROPERTY_KEY, "LOG");

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class)).isEqualTo(Banner.Mode.LOG);
        }

        @Test
        void disablesSpringBootBannerWhenNotSet() {
            assertThat(mockEnvironment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class)).isNull();

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(BANNER_MODE_PROPERTY_KEY, Banner.Mode.class)).isEqualTo(Banner.Mode.OFF);
        }

        @Test
        void maintainsTracingSamplingPropability() {
            mockEnvironment.setProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, "0.1");

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class)).isEqualTo(0.1F);
        }

        @Test
        void configuresTracingSamplingProbabilityWhenNotSet() {
            assertThat(mockEnvironment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class)).isNull();

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(TRACING_SAMPLING_PROBABILITY_PROPERTY_KEY, Float.class)).isEqualTo(1.0F);
        }

        @Test
        void maintainsLoggingStructuredFormatConsole() {
            mockEnvironment.setProperty(LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY, CommonStructuredLogFormat.GRAYLOG_EXTENDED_LOG_FORMAT.name());

            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY, CommonStructuredLogFormat.class)).isEqualTo(CommonStructuredLogFormat.GRAYLOG_EXTENDED_LOG_FORMAT);
        }

        @Test
        void loggingStructuredFormatConsoleIsLogstashWhenProfileLocalIsNotActive() {
            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY, CommonStructuredLogFormat.class)).isEqualTo(CommonStructuredLogFormat.LOGSTASH);
        }

        @Test
        void loggingStructuredFormatConsoleIsNotSetWhenProfileLocalIsActive() {
            mockEnvironment.setActiveProfiles("local");
            observabilityEnvironmentPostProcessorUnderTest.postProcessEnvironment(mockEnvironment, springApplication);
            assertThat(mockEnvironment.getProperty(LOGGING_STRUCTURED_FORMAT_CONSOLE_PROPERTY_KEY, CommonStructuredLogFormat.class)).isNull();
        }

    }

}
