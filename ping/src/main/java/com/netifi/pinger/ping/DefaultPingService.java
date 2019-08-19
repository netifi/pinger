package com.netifi.pinger.ping;

import com.netifi.pinger.pong.PongRequest;
import com.netifi.pinger.pong.PongServiceClient;
import com.netifi.spring.core.annotation.Group;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
public class DefaultPingService {

  private static final Logger log = LoggerFactory.getLogger(DefaultPingService.class);

  private final PongServiceClient pongServiceClient;
  private Disposable ping;
  private AtomicLong requestCounter = new AtomicLong();

  public DefaultPingService(
      @Group("sap.pinger.pong") PongServiceClient pongServiceClient) {
    this.pongServiceClient = pongServiceClient;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void startPinging() {
    ping = Flux.interval(Duration.ofMillis(50)).onBackpressureBuffer().subscribe(this::ping);
  }

  private void ping(Long l) {
    log.info("sending ping {}", l);
    requestCounter.incrementAndGet();
    long startTime = System.currentTimeMillis();
    pongServiceClient.sendPong(PongRequest.newBuilder().setMessage(l.toString()).build())
        .subscribe(pongResponse -> {
          long endTime = System.currentTimeMillis();
          log.info("got response: {} - took: {}", pongResponse.getMessage(), (endTime - startTime));
        });
  }

  @PreDestroy
  public void Dispose() {
    if (!ping.isDisposed()) {
      ping.dispose();
    }
  }

  public Long getCounter() {
    return requestCounter.get();
  }

}
