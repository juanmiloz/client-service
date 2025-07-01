package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.helpers;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.ClientDAO;
import com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data.PersonDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    PersonDAO toPersonDAO(Client client);

    @Mapping(source = "id", target = "clientId")
    ClientDAO toClientDAO(Client client);

    @Mapping(source = "personDAO.id", target = "id")
    @Mapping(source = "personDAO.name", target = "name")
    @Mapping(source = "personDAO.gender", target = "gender")
    @Mapping(source = "personDAO.age", target = "age")
    @Mapping(source = "personDAO.identification", target = "identification")
    @Mapping(source = "personDAO.address", target = "address")
    @Mapping(source = "personDAO.number", target = "number")
    @Mapping(source = "clientDAO.password", target = "password")
    @Mapping(source = "clientDAO.status", target = "status")
    Client toClient(PersonDAO personDAO, ClientDAO clientDAO);

}
