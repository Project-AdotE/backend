package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {

    Page<AnimalEntity> findAllByOrganizacao_Id(Long organizacaoId, Pageable pageable);
    Page<AnimalEntity> findAll(Pageable pageable);

    Optional<AnimalEntity> findById(Long id);

    void deleteById(Long id);

}
