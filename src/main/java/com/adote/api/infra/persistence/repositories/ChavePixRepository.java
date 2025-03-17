package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.ChavePixEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChavePixRepository extends JpaRepository<ChavePixEntity, Integer> {
}
