package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {


    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumer;

    @EventListener(ApplicationReadyEvent.class)
    public Flux<Object> listenMessages() {
        return kafkaConsumer
                .receiveAutoAck()
                .publishOn(Schedulers.newBoundedElastic(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "kafka"))
                .flatMap(record -> {
                    log.info("Record received {}", record.value());
                    return Mono.empty();
                }).
                doOnError(error -> log.error(error.getMessage(), error))
                .retry()
                .repeat();
    }

}
