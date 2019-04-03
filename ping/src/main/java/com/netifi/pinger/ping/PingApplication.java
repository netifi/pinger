package com.netifi.pinger.ping;

import io.netifi.proteus.rsocket.transport.BrokerAddressSelectors;
import io.netifi.proteus.springboot.ProteusConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PingApplication {

  public static void main(String[] args) {
    SpringApplication.run(PingApplication.class, args);
  }
}
