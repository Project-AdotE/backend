package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.ChavePixEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChavePixRepository extends JpaRepository<ChavePixEntity, Integer> {
    List<ChavePixEntity> findAllByOrganizacao_Id(Long organizacaoId);
}
