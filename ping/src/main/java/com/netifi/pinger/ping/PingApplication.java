package com.netifi.pinger.ping;

import java.net.URI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class PingApplication {

  public static void main(String[] args) {
    SpringApplication.run(PingApplication.class, args);
  }

  @Bean
  RouterFunction<ServerResponse> routerFunction() {
    return RouterFunctions.route(
        RequestPredicates.GET("/"),
        req -> ServerResponse.temporaryRedirect(URI.create("/actuator")).build());
  }
}
