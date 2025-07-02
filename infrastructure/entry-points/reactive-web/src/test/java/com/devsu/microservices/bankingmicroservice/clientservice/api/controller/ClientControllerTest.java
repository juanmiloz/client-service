package com.devsu.microservices.bankingmicroservice.clientservice.api.controller;

import com.devsu.microservices.bankingmicroservice.clientservice.api.controllers.ClientController;
import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.NewClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.UpdateClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.usecase.ClientCrudUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {


    @Mock
    private ClientCrudUseCase clientCrudUseCase;

    @InjectMocks
    private ClientController clientController;

    private Client testClient;
    private NewClientDTO newClientDTO;
    private UpdateClientDTO updateClientDTO;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .build();

        newClientDTO = NewClientDTO.builder()
                .name("John Doe")
                .build();

        updateClientDTO = UpdateClientDTO.builder()
                .clientId(testClient.getId())
                .name("John Updated")
                .build();
    }

    @Test
    void createClient_ShouldReturnCreatedClient() {
        when(clientCrudUseCase.createClient(any(Client.class)))
                .thenReturn(Mono.just(testClient));

        Mono<ResponseEntity<Client>> result = clientController.createClient(newClientDTO);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.CREATED &&
                                response.getBody().equals(testClient))
                .verifyComplete();
    }

    @Test
    void getAllClients_ShouldReturnAllClients() {
        when(clientCrudUseCase.getAllClients())
                .thenReturn(Flux.just(testClient));

        Flux<Client> result = clientController.getAllClients();

        StepVerifier.create(result)
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void getClientById_ShouldReturnClient() {
        when(clientCrudUseCase.getClientById(testClient.getId()))
                .thenReturn(Mono.just(testClient));

        Mono<ResponseEntity<Client>> result = clientController.getClientById(testClient.getId());

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().equals(testClient))
                .verifyComplete();
    }

    @Test
    void updateClient_ShouldReturnUpdatedClient() {
        when(clientCrudUseCase.updateClient(any(Client.class)))
                .thenReturn(Mono.just(testClient));

        Mono<ResponseEntity<Client>> result = clientController.updateClient(updateClientDTO);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody().equals(testClient))
                .verifyComplete();
    }

    @Test
    void deleteClient_ShouldReturnNoContent() {
        when(clientCrudUseCase.deleteClient(testClient.getId()))
                .thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = clientController.deleteClient(testClient.getId());

        StepVerifier.create(result)
                .verifyComplete();
    }

}
