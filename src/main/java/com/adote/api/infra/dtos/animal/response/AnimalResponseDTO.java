package com.adote.api.infra.dtos.animal.response;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.infra.dtos.fotoAnimal.response.FotoAnimalResponseDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public record AnimalResponseDTO(
        String nome,
        SexoEnum sexo,
        PorteEnum porte,
        Boolean vacinado,
        OrganizacaoResponseDTO organizacao,
        List<FotoAnimalResponseDTO> fotos
) {
}
