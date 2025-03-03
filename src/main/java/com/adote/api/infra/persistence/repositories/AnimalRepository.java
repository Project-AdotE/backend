package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {

    Page<AnimalEntity> findAllByOrganizacao_IdOrderByCreatedAtDesc(Long organizacaoId, Pageable pageable);
    Page<AnimalEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<AnimalEntity> findById(Long id);

    void deleteById(Long id);

}
