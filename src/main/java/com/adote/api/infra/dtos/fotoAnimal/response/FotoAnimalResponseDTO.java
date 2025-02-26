package com.adote.api.infra.dtos.fotoAnimal.response;

import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;

public record FotoAnimalResponseDTO(
        Long id,
        String url,
        AnimalResponseDTO animal
) {
}
