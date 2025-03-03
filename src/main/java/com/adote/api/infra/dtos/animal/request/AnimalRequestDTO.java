package com.adote.api.infra.dtos.animal.request;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Organizacao;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AnimalRequestDTO(
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
        Long organizacao_id) {
}
