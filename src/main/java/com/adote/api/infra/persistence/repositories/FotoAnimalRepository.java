package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoAnimalRepository extends JpaRepository<FotoAnimalEntity, Integer> {
    List<FotoAnimalEntity> getFotoAnimalEntitiesByAnimal_Id(Long animalId);
}
