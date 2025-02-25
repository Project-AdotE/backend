package com.adote.api.infra.dtos.animal.request;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.entities.Organizacao;

public record AnimalRequestDTO(
        String nome,
        SexoEnum sexo,
        PorteEnum porte,
        Boolean vacinado,
        String url_foto,
        Long organizacao_id) {
}
