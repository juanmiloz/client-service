package com.devsu.microservices.bankingmicroservice.clientservice.model.exceptions;

import com.devsu.microservices.bankingmicroservice.clientservice.model.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomainException extends RuntimeException {

    private final ErrorType errorType;
    private final String message;

    public DomainException(ErrorType errorType) {
        super(String.format(errorType.getDescription()));
        this.message = String.format(errorType.getDescription());
        this.errorType = errorType;
    }

    public DomainException(ErrorType errorType, String... args) {
        super(String.format(errorType.getDescription(), args));
        this.message = String.format(errorType.getDescription(), args);
        this.errorType = errorType;
    }

}
