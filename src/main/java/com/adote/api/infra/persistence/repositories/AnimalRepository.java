package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer>, JpaSpecificationExecutor<AnimalEntity> {
    Optional<AnimalEntity> findById(Long id);
}
