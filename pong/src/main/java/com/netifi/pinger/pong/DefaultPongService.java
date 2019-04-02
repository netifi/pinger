package com.netifi.pinger.pong;

import io.netty.buffer.ByteBuf;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultPongService implements PongService {

  private static AtomicLong requestCounter = new AtomicLong();

  private static final Logger log = LoggerFactory.getLogger(DefaultPongService.class);

  public DefaultPongService() {
  }

  @Override
  public Mono<PongResponse> sendPong(PongRequest message, ByteBuf metadata) {
    log.info("received ping: {}", message.getMessage());
    requestCounter.incrementAndGet();
    return Mono.just(PongResponse.newBuilder().setMessage("pong-" + message.getMessage()).build());
  }

  public Long getCounter() {
    return requestCounter.get();
  }
}
