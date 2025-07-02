package com.devsu.microservices.bankingmicroservice.clientservice.usecase;

import com.devsu.microservices.bankingmicroservice.clientservice.model.enums.ErrorType;
import com.devsu.microservices.bankingmicroservice.clientservice.model.exceptions.DomainException;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientRepository;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientVerificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientVerificationUseCase {

    private final ClientRepository clientRepository;
    private final ClientVerificationProducer clientVerificationProducer;

    public Mono<Void> verifyClient(UUID uuid) {
        return clientRepository.getClientById(uuid)
                .switchIfEmpty(
                        Mono.error(new DomainException(
                                ErrorType.NOT_FOUND, "Client not found"))
                )
                .flatMap(client -> clientVerificationProducer.senderVerificationResponse(client.getName()));

    }

}
