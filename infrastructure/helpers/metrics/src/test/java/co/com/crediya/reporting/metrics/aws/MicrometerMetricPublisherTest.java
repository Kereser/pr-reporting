package co.com.crediya.reporting.metrics.aws;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import software.amazon.awssdk.metrics.internal.EmptyMetricCollection;

class MicrometerMetricPublisherTest {

  @Test
  void metricTest() {
    LoggingMeterRegistry loggingMeterRegistry =
        LoggingMeterRegistry.builder(LoggingRegistryConfig.DEFAULT).build();

    MicrometerMetricPublisher micrometerMetricPublisher =
        new MicrometerMetricPublisher(loggingMeterRegistry);

    micrometerMetricPublisher.publish(EmptyMetricCollection.create());
    micrometerMetricPublisher.close();

    assertNotNull(micrometerMetricPublisher);
  }
}
