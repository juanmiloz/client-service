package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data.VerificationResponse;
import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.helpers.VerificationResponseMapper;
import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.producer.ClientVerificationResponseProducer;
import com.devsu.microservices.bankingmicroservice.clientservice.model.events.AccountCreationRequested;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientVerificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientVerificationResponseAdapter implements ClientVerificationProducer {

    private final ClientVerificationResponseProducer clientVerificationResponseProducer;
    private final VerificationResponseMapper verificationResponseMapper;

    @Override
    public Mono<Void> senderVerificationResponse(AccountCreationRequested account) {
        VerificationResponse accountToCreate = verificationResponseMapper.toVerificationResponse(account);
        return clientVerificationResponseProducer.sendVerificationRequest(accountToCreate);
    }


}
