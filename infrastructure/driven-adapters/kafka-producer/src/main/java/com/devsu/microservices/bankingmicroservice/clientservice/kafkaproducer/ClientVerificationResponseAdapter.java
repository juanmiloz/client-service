package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data.VerificationResponse;
import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.producer.ClientVerificationResponseProducer;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientVerificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientVerificationResponseAdapter implements ClientVerificationProducer {

    private final ClientVerificationResponseProducer clientVerificationResponseProducer;

    @Override
    public Mono<Void> senderVerificationResponse(String clientName) {
        return clientVerificationResponseProducer.sendVerificationRequest(new VerificationResponse(true, clientName));
    }


}
