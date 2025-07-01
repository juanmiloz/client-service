package com.devsu.microservices.bankingmicroservice.clientservice.r2dbc.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("client")
public class ClientDAO {

    @Id
    private UUID clientId;
    private String password;
    private String status;

    @Transient
    private boolean isNew = true;

    public ClientDAO(String password, String status) {
        this.password = password;
        this.status = status;
    }

}
