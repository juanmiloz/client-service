package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.helpers;

import com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data.VerificationResponse;
import com.devsu.microservices.bankingmicroservice.clientservice.model.events.AccountCreationRequested;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerificationResponseMapper {

    VerificationResponse toVerificationResponse(AccountCreationRequested account);

}
