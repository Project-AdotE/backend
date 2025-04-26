package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.PerguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<PerguntaEntity, Long> {
    List<PerguntaEntity> findAllByOrderByPosicaoAsc();
}
