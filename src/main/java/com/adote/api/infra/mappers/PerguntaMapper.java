package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Pergunta;
import com.adote.api.infra.dtos.perguntas.PerguntaResponseDTO;
import com.adote.api.infra.persistence.entities.PerguntaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerguntaMapper {

    Pergunta toDomain(PerguntaEntity entity);

    PerguntaEntity toEntity(Pergunta domain);

    PerguntaResponseDTO toResponseDTO(Pergunta domain);

}
