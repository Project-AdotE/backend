package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.RespostasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostasRepository extends JpaRepository<RespostasEntity, Long> {
    List<RespostasEntity> findAllByFormulario_Id(Long formularioId);

    void deleteByFormulario_Id(Long formularioId);
}
