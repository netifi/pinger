package com.netifi.pinger.ping;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PingPrinter {

  private final DefaultPingService defaultPingService;

  public PingPrinter(DefaultPingService defaultPingService) {
    this.defaultPingService = defaultPingService;
  }

  @Scheduled(fixedDelay = 10000, initialDelay = 10000)
  public void printReport() {
    System.out.println("\n===========================");
    System.out.println(defaultPingService.printReport());
    System.out.println("===========================\n");
  }
}
