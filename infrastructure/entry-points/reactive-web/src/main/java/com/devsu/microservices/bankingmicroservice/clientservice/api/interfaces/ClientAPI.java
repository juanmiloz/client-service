package com.devsu.microservices.bankingmicroservice.clientservice.api.interfaces;

import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.NewClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.api.data.request.UpdateClientDTO;
import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequestMapping("/api/client")
public interface ClientAPI {

    @PostMapping
    Mono<ResponseEntity<Client>> createClient(@Valid @RequestBody NewClientDTO dto);

    @GetMapping
    Flux<Client> getAllClients();

    @GetMapping("/{id}")
    Mono<ResponseEntity<Client>> getClientById(@PathVariable UUID id);

    @PutMapping
    Mono<ResponseEntity<Client>> updateClient(@Valid @RequestBody UpdateClientDTO dto);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Void>> deleteClient(@PathVariable UUID id);
}
