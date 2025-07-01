package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.repositories;

import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.ClientDAO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ClientDAORepository extends R2dbcRepository<ClientDAO, UUID> {
}
