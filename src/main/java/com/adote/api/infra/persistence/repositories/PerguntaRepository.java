package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.PerguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<PerguntaEntity, Long> {
}
