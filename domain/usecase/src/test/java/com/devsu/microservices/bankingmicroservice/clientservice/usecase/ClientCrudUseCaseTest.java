package com.devsu.microservices.bankingmicroservice.clientservice.usecase;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.model.enums.ErrorType;
import com.devsu.microservices.bankingmicroservice.clientservice.model.exceptions.DomainException;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientCrudUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientCrudUseCase clientCrudUseCase;

    private Client testClient;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testClient = Client.builder()
                .id(testId)
                .name("John Doe")
                .build();
    }

    @Test
    void createClient_ShouldReturnCreatedClient() {
        when(clientRepository.createClient(any(Client.class)))
                .thenReturn(Mono.just(testClient));

        Mono<Client> result = clientCrudUseCase.createClient(testClient);

        StepVerifier.create(result)
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void getClientById_ShouldReturnClient_WhenExists() {
        when(clientRepository.getClientById(testId))
                .thenReturn(Mono.just(testClient));

        Mono<Client> result = clientCrudUseCase.getClientById(testId);

        StepVerifier.create(result)
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void getClientById_ShouldThrowException_WhenNotFound() {
        when(clientRepository.getClientById(testId))
                .thenReturn(Mono.empty());

        Mono<Client> result = clientCrudUseCase.getClientById(testId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().equals("Resource not found: Client not found"))
                .verify();
    }

    @Test
    void getAllClients_ShouldReturnAllClients() {
        when(clientRepository.getAllClients())
                .thenReturn(Flux.just(testClient));

        Flux<Client> result = clientCrudUseCase.getAllClients();

        StepVerifier.create(result)
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void updateClient_ShouldReturnUpdatedClient_WhenExists() {
        when(clientRepository.getClientById(testId))
                .thenReturn(Mono.just(testClient));
        when(clientRepository.updateClient(any(Client.class)))
                .thenReturn(Mono.just(testClient));

        Mono<Client> result = clientCrudUseCase.updateClient(testClient);

        StepVerifier.create(result)
                .expectNext(testClient)
                .verifyComplete();
    }

    @Test
    void updateClient_ShouldThrowException_WhenNotFound() {
        when(clientRepository.getClientById(testId))
            .thenReturn(Mono.empty());

        Mono<Client> result = clientCrudUseCase.updateClient(testClient);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().equals("Resource not found: Client not found"))
                .verify();    }

    @Test
    void deleteClient_ShouldComplete_WhenExists() {
        when(clientRepository.getClientById(testId))
                .thenReturn(Mono.just(testClient));
        when(clientRepository.deleteClient(testId))
                .thenReturn(Mono.empty());

        Mono<Void> result = clientCrudUseCase.deleteClient(testId);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteClient_ShouldThrowException_WhenNotFound() {
        when(clientRepository.getClientById(testId))
                .thenReturn(Mono.empty());

        Mono<Void> result = clientCrudUseCase.deleteClient(testId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().equals("Resource not found: Client not found"))
                .verify();
    }
}
