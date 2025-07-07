package com.devsu.microservices.bankingmicroservice.clientservice.model.gateway;

import com.devsu.microservices.bankingmicroservice.clientservice.model.events.AccountCreationRequested;
import reactor.core.publisher.Mono;

public interface ClientVerificationProducer {

    Mono<Void> senderVerificationResponse(AccountCreationRequested account);

}
