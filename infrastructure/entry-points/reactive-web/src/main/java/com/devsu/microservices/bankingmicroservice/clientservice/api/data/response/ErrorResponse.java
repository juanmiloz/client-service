package com.devsu.microservices.bankingmicroservice.clientservice.api.data.response;

public record ErrorResponse (
        int status,
        String errorCode,
        String message
){
}
