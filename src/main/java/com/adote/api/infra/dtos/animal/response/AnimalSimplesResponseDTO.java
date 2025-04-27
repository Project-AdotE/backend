package com.adote.api.infra.dtos.animal.response;

import com.adote.api.infra.dtos.fotoAnimal.response.FotoAnimalResponseDTO;

public record AnimalSimplesResponseDTO(
        Long id,
        String nome,
        FotoAnimalResponseDTO fotos
) {
}
