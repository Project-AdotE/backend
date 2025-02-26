package com.adote.api.infra.dtos.fotoAnimal.response;

import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record FotoAnimalResponseDTO(
        Long id,
        String url,
        @JsonBackReference AnimalResponseDTO animal
) {
}
