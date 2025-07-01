package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "person")
public class PersonDAO {

    @Id
    private UUID id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String number;

}
