package com.adote.api.infra.dtos.animal.response;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;

public record AnimalResponseDTO(
        String nome,
        SexoEnum sexo,
        PorteEnum porte,
        Boolean vacinado,
        OrganizacaoResponseDTO organizacao
) {
}
