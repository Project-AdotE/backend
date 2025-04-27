package com.adote.api.infra.dtos.formulario.response;

import com.adote.api.infra.dtos.animal.response.AnimalSimplesResponseDTO;

public record AnimalComFormResponseDTO(
        Long id,
        AnimalSimplesResponseDTO animal,
        Integer formulariosEnviados
) {
}
