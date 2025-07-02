package com.devsu.microservices.bankingmicroservice.clientservice.model.gateway;

import reactor.core.publisher.Mono;

public interface ClientVerificationProducer {

    Mono<Void> senderVerificationResponse(String clientName);

}
