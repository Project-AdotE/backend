package com.adote.api.infra.mappers;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.infra.dtos.fotoAnimal.response.FotoAnimalResponseDTO;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FotoAnimalMapper {

    FotoAnimalEntity toEntity(FotoAnimal fotoAnimal);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "animal", source = "animal", ignore = true)
    FotoAnimal toFoto(FotoAnimalEntity fotoAnimalEntity);

    @Named("mapWithoutAnimal")
    @Mapping(target = "animal", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "url", source = "url")
    FotoAnimal toFotoWithoutAnimal(FotoAnimalEntity fotoAnimalEntity);

    FotoAnimalResponseDTO toResponseDTO(FotoAnimal fotoAnimal);
}