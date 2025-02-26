package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoAnimalRepository extends JpaRepository<FotoAnimalEntity, Integer> {
}
