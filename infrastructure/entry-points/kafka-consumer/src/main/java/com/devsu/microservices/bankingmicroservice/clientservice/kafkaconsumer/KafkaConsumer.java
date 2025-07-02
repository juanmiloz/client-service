package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer;

import com.devsu.microservices.bankingmicroservice.clientservice.usecase.ClientVerificationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumer;
    private final ClientVerificationUseCase clientVerificationUseCase;


    @EventListener(ApplicationReadyEvent.class)
    public Flux<Object> consumerRecord(){
        return  kafkaConsumer.receive()
                .flatMap(record -> {
                    String msg = record.value();
                    log.info("Received: {}", msg);
                    return clientVerificationUseCase
                            .verifyClient(UUID.fromString(msg))
                            .then(Mono.fromRunnable(record.receiverOffset()::acknowledge));
                })
                .doOnError(e -> log.error("Consumer error: {}", e.getMessage()));
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public Flux<Void> listenMessages() {
//        return kafkaConsumer
//                .receiveAutoAck()
//                .publishOn(Schedulers.newBoundedElastic(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "kafka"))
//                .flatMap(record -> {
//                    log.info("Record received {}", record.value());
//                    return clientVerificationUseCase.verifyClient(UUID.fromString(record.value()));
//                }).
//                doOnError(error -> log.error(error.getMessage(), error))
//                .retry()
//                .repeat();
//    }

}
