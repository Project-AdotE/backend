package com.adote.api.infra.mappers;


import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalEntity toEntity(Animal animal);
    AnimalEntity toEntity(AnimalRequestDTO animalRequestDTO);

    Animal toAnimal(AnimalEntity animalEntity);
    Animal toAnimal(AnimalRequestDTO animalRequestDTO);

    AnimalResponseDTO toResponseDTO(Animal animal);
}
