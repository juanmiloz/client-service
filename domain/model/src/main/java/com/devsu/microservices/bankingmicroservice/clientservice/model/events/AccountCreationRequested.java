package com.devsu.microservices.bankingmicroservice.clientservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AccountCreationRequested {

    private UUID clientId;
    private String accountNumber;
    private String clientName;
    private String accountType;
    private Double initialBalance;
    private Boolean status;

}
