package com.devsu.microservices.bankingmicroservice.clientservice.kafkaconsumer.data.response;

import com.devsu.microservices.bankingmicroservice.clientservice.model.events.AccountCreationRequested;

import java.util.UUID;

public record AccountVerificationResponse (

        UUID clientId,
        String accountNumber,
        String accountType,
        Double initialBalance,
        Boolean status
)
{

    public static AccountCreationRequested toAccount(AccountVerificationResponse account) {
        return new AccountCreationRequested(
                account.clientId,
                account.accountNumber,
                null,
                account.accountType,
                account.initialBalance,
                account.status
        );
    }

}
