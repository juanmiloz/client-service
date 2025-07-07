package com.devsu.microservices.bankingmicroservice.clientservice.usecase;

import com.devsu.microservices.bankingmicroservice.clientservice.model.enums.ErrorType;
import com.devsu.microservices.bankingmicroservice.clientservice.model.events.AccountCreationRequested;
import com.devsu.microservices.bankingmicroservice.clientservice.model.exceptions.DomainException;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientRepository;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientVerificationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClientVerificationUseCase {

    private final ClientRepository clientRepository;
    private final ClientVerificationProducer clientVerificationProducer;

    public Mono<Void> verifyClient(AccountCreationRequested accountCreationRequested) {
        return clientRepository.getClientById(accountCreationRequested.getClientId())
                .switchIfEmpty(
                        Mono.defer(() -> {
                            log.error("Client not found with ID: {}. Account creation will be rejected.",
                                    accountCreationRequested.getClientId());
                            return Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found"));
                        })
                )
                .flatMap(client -> {
                    accountCreationRequested.setClientName(client.getName());
                    return clientVerificationProducer.senderVerificationResponse(accountCreationRequested);
                });

    }

}
