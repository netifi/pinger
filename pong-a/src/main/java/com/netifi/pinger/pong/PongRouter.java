package com.netifi.pinger.pong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PongRouter {

  @Bean
  public RouterFunction<ServerResponse> route(PongHandler pongHandler) {
    return RouterFunctions.route(RequestPredicates.GET("/pong").and(RequestPredicates.accept(
        MediaType.TEXT_PLAIN)), pongHandler::pong);
  }

}
