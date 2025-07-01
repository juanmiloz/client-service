package com.devsu.microservices.bankingmicroservice.clientservice.usecase;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.model.enums.ErrorType;
import com.devsu.microservices.bankingmicroservice.clientservice.model.exceptions.DomainException;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientCrudUseCase {

    private final ClientRepository clientRepository;


    public Mono<Client> createClient(Client client) {
        return Mono.just(client)
                .flatMap(clientRepository::createClient);
    }

    public Mono<Client> getClientById(UUID id) {
        return clientRepository.getClientById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")));
    }

    public Flux<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public Mono<Client> updateClient(Client client) {
        return clientRepository.getClientById(client.getId())
                .switchIfEmpty(
                        Mono.error(new DomainException(
                                ErrorType.NOT_FOUND, "Client not found"))
                )
                .flatMap(existing -> clientRepository.updateClient(client));
    }

    public Mono<Void> deleteClient(UUID id) {
        return clientRepository.getClientById(id)
                .switchIfEmpty(
                        Mono.error(new DomainException(
                                ErrorType.NOT_FOUND, "Client not found"))
                )
                .flatMap(existing -> clientRepository.deleteClient(id));
    }

}
