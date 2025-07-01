package com.devsu.microservices.bankingmicroservice.clientservice.api.controllers;

import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.NewClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.UpdateClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.api.interfaces.ClientAPI;
import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.usecase.ClientCrudUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientAPI {

    private final ClientCrudUseCase clientCrudUseCase;

    @Override
    public Mono<ResponseEntity<Client>> createClient(NewClientDTO dto) {
        return clientCrudUseCase
                .createClient(NewClientDTO.toClient(dto))
                .map(client -> ResponseEntity.status(HttpStatus.CREATED).body(client));
    }

    @Override
    public Flux<Client> getAllClients() {
        return clientCrudUseCase.getAllClients();
    }

    @Override
    public Mono<ResponseEntity<Client>> getClientById(UUID id) {
        return clientCrudUseCase
                .getClientById(id)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Client>> updateClient(UpdateClientDTO dto) {
        return clientCrudUseCase
                .updateClient(UpdateClientDTO.toClient(dto))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteClient(UUID id) {
        return clientCrudUseCase
                .deleteClient(id)
                .map(v -> ResponseEntity.noContent().<Void>build());
    }

}
