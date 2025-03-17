package com.adote.api.infra.mappers;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixResponseDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.persistence.entities.ChavePixEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChavePixMapper {

    ChavePixEntity toEntity(ChavePixRequestDTO chavePixRequestDTO);

    ChavePix toChavePix(ChavePixEntity chavePixEntity);

    ChavePixResponseDTO toResponseDTO(ChavePix chavePix);

    ChavePixSimplificadaDTO toSimplificadaDTO(ChavePix chavePix);

}
