package com.adote.api.infra.dtos.animal.response;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.infra.dtos.fotoAnimal.response.FotoAnimalResponseDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoBaseDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public record AnimalResponseDTO(
        Long id,
        String nome,
        TipoAnimalEnum tipo,
        SexoEnum sexo,
        PorteEnum porte,
        IdadeEnum idade,
        Boolean vacinado,
        Boolean castrado,
        Boolean vermifugado,
        Boolean srd,
        String descricao,
        OrganizacaoBaseDTO organizacao,
        List<FotoAnimalResponseDTO> fotos
) {
}
