package com.netifi.pinger.pong;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class PongInfoContributor implements InfoContributor {

  private final DefaultPongService defaultPongService;

  public PongInfoContributor(
      DefaultPongService defaultPongService) {
    this.defaultPongService = defaultPongService;
  }

  @Override
  public void contribute(Builder builder) {
    builder.withDetail("name", defaultPongService.getName());
    builder.withDetail("totalResponseCount", defaultPongService.getCounter());
  }
}
