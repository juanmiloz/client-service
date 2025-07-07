package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.data.response.AccountVerificationResponse;
import com.devsu.microservices.bankingmicroservice.clientservice.usecase.ClientVerificationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, AccountVerificationResponse> kafkaConsumer;
    private final ClientVerificationUseCase clientVerificationUseCase;


    @EventListener(ApplicationReadyEvent.class)
    public Flux<Object> consumerRecord(){
        return  kafkaConsumer.receive()
                .flatMap(record -> {
                    AccountVerificationResponse accountToVerify = record.value();
                    log.info("Received: {}", accountToVerify);
                    return clientVerificationUseCase
                            .verifyClient(AccountVerificationResponse.toAccount(accountToVerify))
                            .then(Mono.fromRunnable(record.receiverOffset()::acknowledge));
                })
                .doOnError(e -> log.error("Consumer error: {}", e.getMessage()));
    }
}
