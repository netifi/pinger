package com.netifi.pinger.ping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PingHandler {

  private DefaultPingService defaultPingService;

  public PingHandler(DefaultPingService defaultPingService) {
    this.defaultPingService = defaultPingService;
  }

  public Mono<ServerResponse> ping(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters
            .fromObject(defaultPingService.printReport()));
  }
}
