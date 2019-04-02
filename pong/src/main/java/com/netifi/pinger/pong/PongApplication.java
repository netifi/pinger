package com.netifi.pinger.pong;

import io.netifi.proteus.rsocket.transport.BrokerAddressSelectors;
import io.netifi.proteus.springboot.ProteusConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PongApplication {

  public static void main(String[] args) {
    SpringApplication.run(PongApplication.class, args);
  }

  @Bean
  public ProteusConfigurer proteusConfigurer() {

    return builder ->
        builder.addressSelector(BrokerAddressSelectors.TCP_ADDRESS);
  }
}
