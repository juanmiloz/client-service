package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.producer;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data.VerificationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientVerificationResponseProducer {

    private final ReactiveKafkaProducerTemplate<String, VerificationResponse> kafkaTemplate;
    private static final String TOPIC = "client-verification-response";
    private static final String KEY = "response";

    public Mono<Void> sendVerificationRequest(VerificationResponse verificationResponse) {
        ProducerRecord<String, VerificationResponse> record = new ProducerRecord<>(TOPIC, KEY, verificationResponse);

        return kafkaTemplate.send(record).log("kafka-send")
                .doOnError(e ->
                        System.err.println("[KAFKA] error to send: " + e.getMessage())
                )
                .then();
    }

}
