package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.repositories;

import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.PersonDAO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface PersonDAORepository extends R2dbcRepository<PersonDAO, UUID> {
}
