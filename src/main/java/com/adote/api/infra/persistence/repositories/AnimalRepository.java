package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {

    List<AnimalEntity> findAllByOrganizacao_Id(Long organizacaoId);

    List<AnimalEntity> findAllByOrganizacao_IdOrderBy

    Optional<AnimalEntity> findById(Long id);
}
