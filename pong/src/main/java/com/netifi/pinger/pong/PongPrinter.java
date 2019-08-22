package com.netifi.pinger.pong;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PongPrinter {

  private final DefaultPongService defaultPongService;

  public PongPrinter(DefaultPongService defaultPongService) {
    this.defaultPongService = defaultPongService;
  }

  @Scheduled(fixedDelay = 10000, initialDelay = 10000)
  public void printReport() {
    System.out.println("\n===========================");
    System.out.println(defaultPongService.printReport());
    System.out.println("===========================\n");
  }
}
