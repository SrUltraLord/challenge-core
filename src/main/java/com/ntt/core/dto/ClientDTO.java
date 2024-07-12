package com.ntt.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ClientDTO(
        @JsonProperty("Id")
        Long id,
        @JsonProperty("Nombre")
        String name,
        @JsonProperty("Genero")
        String gender,
        @JsonProperty("Edad")
        Integer age,
        @JsonProperty("Identificador")
        String identifier,
        @JsonProperty("Direccion")
        String address,
        @JsonProperty("Telefono")
        String phone
) {
}
