package com.devsu.microservices.bankingmicroservice.clientservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Person {

    private UUID id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String number;

}
