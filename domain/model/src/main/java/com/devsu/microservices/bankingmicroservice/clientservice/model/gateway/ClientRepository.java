package com.devsu.microservices.bankingmicroservice.clientservice.model.gateway;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientRepository {

    Mono<Client> createClient(Client client);

    Mono<Client> getClientById(UUID id);

    Flux<Client> getAllClients();

    Mono<Client> updateClient(Client client);

    Mono<Void> deleteClient(UUID id);

}
