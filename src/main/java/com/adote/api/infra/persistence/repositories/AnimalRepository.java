package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {
}
