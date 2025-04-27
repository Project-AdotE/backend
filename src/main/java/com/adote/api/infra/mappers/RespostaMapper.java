package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Respostas;
import com.adote.api.infra.dtos.formulario.request.RespostaRequestDTO;
import com.adote.api.infra.persistence.entities.RespostasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RespostaMapper {

    Respostas toDomain(RespostasEntity entity);

    RespostasEntity toEntity(Respostas domain);
    RespostasEntity toEntity(RespostaRequestDTO requestDTO);
}
