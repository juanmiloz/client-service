package com.devsu.microservices.bankingmicroservice.clientservice.api.data.request;

import com.devsu.microservices.bankingmicroservice.clientservice.model.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record NewClientDTO(
        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must be at most 100 characters.")
        String name,

        @NotBlank(message = "Gender is required.")
        @Size(max = 50, message = "Gender must be at most 50 characters.")
        String gender,

        @NotNull(message = "Age is required.")
        @Min(value = 0, message = "Age must be positive.")
        Integer age,

        @NotBlank(message = "Identification is required.")
        @Size(max = 20, message = "Identification must be at most 20 characters.")
        String identification,

        @NotBlank(message = "Address is required.")
        @Size(max = 100, message = "Address must be at most 100 characters.")
        String address,

        @NotBlank(message = "Phone number is required.")
        @Size(max = 10, message = "Phone number must be at most 10 characters.")
        String number,

        @NotBlank(message = "Password is required.")
        @Size(max = 245, message = "Password must be at most 245 characters.")
        String password,

        @NotBlank(message = "Status is required.")
        @Size(max = 255, message = "Status must be at most 255 characters.")
        String status
) {

    public static Client toClient(NewClientDTO clientDTO) {
        return Client.builder()
                .name(clientDTO.name())
                .gender(clientDTO.gender())
                .age(clientDTO.age())
                .identification(clientDTO.identification())
                .address(clientDTO.address())
                .number(clientDTO.number())
                .password(clientDTO.password())
                .status(clientDTO.status())
                .build();
    }
}