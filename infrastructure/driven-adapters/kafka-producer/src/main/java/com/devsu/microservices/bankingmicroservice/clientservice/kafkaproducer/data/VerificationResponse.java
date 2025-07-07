package com.devsu.microservices.bankingmicroservice.clientservice.kafkaproducer.data;

import java.util.UUID;

public record VerificationResponse(
        UUID clientId,
        String accountNumber,
        String clientName,
        String accountType,
        Double initialBalance,
        Boolean status
) {
}
