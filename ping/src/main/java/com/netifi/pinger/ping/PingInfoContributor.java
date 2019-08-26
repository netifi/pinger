package com.netifi.pinger.ping;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class PingInfoContributor implements InfoContributor {

  private final DefaultPingService defaultPingService;

  public PingInfoContributor(DefaultPingService defaultPingService) {
    this.defaultPingService = defaultPingService;
  }

  @Override
  public void contribute(Builder builder) {
    builder.withDetail("name", defaultPingService.getName());
    builder.withDetail("interval", defaultPingService.getInterval());
    builder.withDetail("totalRequestCount", defaultPingService.getCounter());
  }
}
