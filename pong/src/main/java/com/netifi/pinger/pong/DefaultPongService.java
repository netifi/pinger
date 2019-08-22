package com.netifi.pinger.pong;

import io.netty.buffer.ByteBuf;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultPongService implements PongService {

  private final String[] names = {"Adam",
      "Alan",
      "Aldo",
      "Alex",
      "Andy",
      "Ari",
      "Ario",
      "Asa",
      "Ben",
      "Blake",
      "Brooks",
      "Bryce",
      "Cal",
      "Chad",
      "Charlie",
      "Chase",
      "Chris",
      "Clay",
      "Cole",
      "Dane",
      "Dan",
      "Dave",
      "Drew",
      "Eli",
      "Enzo",
      "Eric",
      "Ethan",
      "Evan",
      "Felix",
      "Franz",
      "Fritz",
      "Gary",
      "Gray",
      "Gus",
      "Heath",
      "Henry",
      "Hugo",
      "Ian",
      "Jack",
      "Jake",
      "James",
      "Jason",
      "Jay",
      "John",
      "Josh",
      "Lane",
      "Lee",
      "Liam",
      "Luke",
      "Mark",
      "Matt",
      "Max",
      "Miles",
      "Milo",
      "Nate",
      "Nick",
      "Nico",
      "Noah",
      "Owen",
      "Parks",
      "Pat",
      "Paul",
      "Pax",
      "Price",
      "Ray",
      "Rich",
      "Roan",
      "Ross",
      "Ryan",
      "Sam",
      "Scott",
      "Sean",
      "Shay",
      "Ted",
      "Theo",
      "Tom",
      "Troy",
      "Ty",
      "Van",
      "Will",
      "Zack",
      "Zev"};

  private static AtomicLong requestCounter = new AtomicLong();
  private ConcurrentHashMap<String, AtomicLong> responseMap = new ConcurrentHashMap<>();

  private final String name;

  private static final Logger log = LoggerFactory.getLogger(DefaultPongService.class);

  public DefaultPongService( @Value("${demo.netifi.pinger.name:empty}" ) String name) {
    this.name = name.equals("empty") ?  names[new Random().nextInt(names.length)] : name;
    log.info("My name is: {}", this.name);

  }

  @Override
  public Mono<PongResponse> sendPong(PongRequest message, ByteBuf metadata) {
    log.info("{}: REQUEST: {} FROM: {}", this.name, message.getMessage(), message.getSenderName());
    requestCounter.incrementAndGet();
    responseMap.putIfAbsent(message.getSenderName(), new AtomicLong(0));
    responseMap.get(message.getSenderName()).incrementAndGet();
    return Mono.just(PongResponse.newBuilder().setMessage("pong-" + message.getMessage()).setSenderName(this.name).build());
  }

  public Long getCounter() {
    return requestCounter.get();
  }

  public String getName() { return this.name; };

  public Set<Entry<String, AtomicLong>> getMap() {
    return responseMap.entrySet();
  }

  public String printReport() {
    StringBuilder responseText = new StringBuilder();
    responseText.append("my name is: ").append(this.getName()).append("\n");
    responseText.append("we have sent ").append(this.getCounter()).append(" pong requests\n");
    this.getMap().forEach(e -> {
      responseText.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
    });
    return responseText.toString();
  }
}
