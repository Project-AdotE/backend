package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FotoAnimalRepository extends JpaRepository<FotoAnimalEntity, Integer> {
    List<FotoAnimalEntity> getFotoAnimalEntitiesByAnimal_Id(Long animalId);

    Optional<FotoAnimalEntity> findByUrl(String url);
}
