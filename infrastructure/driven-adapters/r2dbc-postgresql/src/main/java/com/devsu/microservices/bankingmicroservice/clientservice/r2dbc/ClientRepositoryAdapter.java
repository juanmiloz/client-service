package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.model.gateway.ClientRepository;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.ClientDAO;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.PersonDAO;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.helpers.ClientMapper;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.repositories.ClientDAORepository;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.repositories.PersonDAORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepository {

    private final PersonDAORepository personDAORepository;
    private final ClientDAORepository clientDAORepository;
    private final R2dbcEntityTemplate template;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public Mono<Client> createClient(Client client) {
        return personDAORepository
                .save(clientMapper.toPersonDAO(client))
                .flatMap(savedPerson -> {
                    ClientDAO clientDAO = new ClientDAO(client.getPassword(), client.getStatus());
                    clientDAO.setClientId(savedPerson.getId());
                    return template
                            .insert(clientDAO)
                            .map(savedClientDAO ->
                                    clientMapper.toClient(savedPerson, savedClientDAO)
                            );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Client> getClientById(UUID id) {
        return personDAORepository.findById(id)
                .flatMap(person -> clientDAORepository.findById(id)
                        .map(client -> clientMapper.toClient(person, client)));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Client> getAllClients() {
        return personDAORepository.findAll()
                .flatMap(person -> clientDAORepository.findById(person.getId())
                        .map(client -> clientMapper.toClient(person, client)));
    }

    @Override
    @Transactional
    public Mono<Client> updateClient(Client client) {
        Mono<PersonDAO> personUpdated = personDAORepository
                .findById(client.getId())
                .flatMap(existingPerson -> {
                    PersonDAO updated = clientMapper.toPersonDAO(client);
                    updated.setId(existingPerson.getId());
                    return personDAORepository.save(updated);      // UPDATE person
                });

        Mono<ClientDAO> clientUpdated = clientDAORepository
                .findById(client.getId())
                .flatMap(existingClient -> {
                    existingClient.setPassword(client.getPassword());
                    existingClient.setStatus(client.getStatus());
                    return clientDAORepository.save(existingClient);  // UPDATE client
                });

        return Mono.zip(personUpdated, clientUpdated)
                .map(tuple ->
                        clientMapper.toClient(tuple.getT1(), tuple.getT2())
                );
    }

    @Override
    @Transactional
    public Mono<Void> deleteClient(UUID id) {
        return clientDAORepository.deleteById(id)
                .then(personDAORepository.deleteById(id));
    }

}
