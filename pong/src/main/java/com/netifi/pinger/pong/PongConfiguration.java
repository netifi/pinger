package com.netifi.pinger.pong;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.dropwizard.DropwizardConfig;
import io.micrometer.core.instrument.dropwizard.DropwizardMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class PongConfiguration {
  @Bean
  public MetricRegistry dropwizardRegistry() {
    return new MetricRegistry();
  }

  @Bean
  public ConsoleReporter consoleReporter(MetricRegistry dropwizardRegistry) {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(dropwizardRegistry)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
    reporter.start(30, TimeUnit.SECONDS);
    return reporter;
  }

  @Bean
  public MeterRegistry consoleLoggingRegistry(MetricRegistry dropwizardRegistry) {
    DropwizardConfig consoleConfig = new DropwizardConfig() {

      @Override
      public String prefix() {
        return "console";
      }

      @Override
      public String get(String key) {
        return null;
      }

    };

    return new DropwizardMeterRegistry(consoleConfig, dropwizardRegistry, HierarchicalNameMapper.DEFAULT, Clock.SYSTEM) {
      @Override
      protected Double nullGaugeValue() {
        return null;
      }
    };
  }
}
