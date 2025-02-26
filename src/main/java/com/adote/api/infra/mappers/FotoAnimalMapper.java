package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FotoAnimalMapper {

    FotoAnimalEntity toEntity(FotoAnimal fotoAnimal);

    FotoAnimal toFoto(FotoAnimalEntity fotoAnimalEntity);


}
