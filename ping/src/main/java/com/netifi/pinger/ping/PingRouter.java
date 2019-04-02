package com.netifi.pinger.ping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PingRouter {

  @Bean
  public RouterFunction<ServerResponse> route(PingHandler pingHandler) {
    return RouterFunctions.route(RequestPredicates.GET("/ping").and(RequestPredicates.accept(
        MediaType.TEXT_PLAIN)), pingHandler::ping);
  }

}
