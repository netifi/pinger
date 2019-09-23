package com.netifi.pinger.ping;

import com.netifi.pinger.pong.PongRequest;
import com.netifi.pinger.pong.PongServiceClient;
import com.netifi.spring.core.annotation.Group;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
public class DefaultPingService {

  private static final Logger log = LoggerFactory.getLogger(DefaultPingService.class);

  private final String[] names = {
    "Abby", "Alice", "Ally", "Amy", "Angel", "Anna", "Anne", "Anya", "April", "Asia", "Ava", "Bay",
    "Becca", "Becky", "Beth", "Brooke", "Cara", "Charlie", "Cindy", "Claire", "Daisy", "Della",
    "Ella", "Elsa", "Emma", "Eve", "Faith", "Grace", "Hana", "Iris", "Isla", "Ivy", "Jade", "Jana",
    "Jenna", "Jill", "Jolie", "Joy", "Julie", "June", "Juno", "Kate", "Kathy", "Katie", "Kelly",
    "Kerry", "Kim", "Kitty", "Lily", "Lisa", "Liz", "Liza", "Lola", "Luz", "Maddie", "Maggie",
    "Mara", "Mary", "Maya", "May", "Misha", "Molly", "Nell", "Nora", "Oona", "Oria", "Paige",
    "Rain", "Raven", "Rhea", "Rose", "Sana", "Sandy", "Sarah", "Sasha", "Tara", "Uma", "Vera",
    "Vicky", "Zadie", "Zara"
  };

  private final PongServiceClient pongServiceClient;
  private final String name;
  private final Duration interval;
  private final MeterRegistry registry;
  private final AtomicLong requestCounter;
  private Disposable ping;

  public DefaultPingService(
      @Group("demo.netifi.pinger.pong") PongServiceClient pongServiceClient,
      @Value("${demo.netifi.pinger.name:empty}") String name,
      @Value("${demo.netifi.pinger.interval:3s}") Duration interval,
      MeterRegistry registry) {
    this.name = name.equals("empty") ? names[new Random().nextInt(names.length)] : name;
    this.pongServiceClient = pongServiceClient;
    this.interval = interval;
    this.registry = registry;
    this.requestCounter = new AtomicLong();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void startPinging() {
    log.info("My name is: {} and I will ping at: {}", this.name, this.interval);
    ping = Flux.interval(this.interval).onBackpressureBuffer().subscribe(this::ping);
  }

  private void ping(Long l) {
    log.info("{}: SENDING: {}", this.name, l);
    requestCounter.incrementAndGet();
    long startTime = System.currentTimeMillis();
    pongServiceClient
        .sendPong(
            PongRequest.newBuilder().setMessage(l.toString()).setSenderName(this.name).build())
        .subscribe(
            pongResponse -> {
              long endTime = System.currentTimeMillis();
              log.info(
                  "{}: RESPONSE: {} FROM: {} TOOK: {}",
                  this.name,
                  pongResponse.getMessage(),
                  pongResponse.getSenderName(),
                  (endTime - startTime));
              registry
                  .timer(
                      "pong.responseTime",
                      Tags.of("name", this.getName(), "senderName", pongResponse.getSenderName()))
                  .record((endTime - startTime), TimeUnit.MILLISECONDS);
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

  public String getName() {
    return this.name;
  }

  public Duration getInterval() {
    return this.interval;
  }

}
