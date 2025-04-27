package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Formulario;
import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;
import com.adote.api.infra.persistence.entities.FormularioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormularioMapper {

    Formulario toDomain(FormularioEntity entity);

    FormularioEntity toEntity(Formulario domain);
    FormularioEntity toEntity(FormularioRequestDTO requestDTO);

}
