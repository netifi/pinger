package com.netifi.pinger.ping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class PingApplication {

  public static void main(String[] args) {
    SpringApplication.run(PingApplication.class, args);
  }
}
