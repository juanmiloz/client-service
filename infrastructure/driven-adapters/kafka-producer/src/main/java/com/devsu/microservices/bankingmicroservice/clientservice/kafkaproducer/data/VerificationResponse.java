package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data;

public record VerificationResponse(
        Boolean clientExist,
        String clientName
) {
}
