package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer>, JpaSpecificationExecutor<AnimalEntity> {
    Optional<AnimalEntity> findById(Long id);

    Optional<AnimalEntity> findByIdAndAdotadoFalse(Long id);

    @Query("SELECT COUNT(a) FROM AnimalEntity a WHERE a.organizacao.id = :organizacaoId AND a.adotado = true")
    Long countAdotadosByOrganizacao(@Param("organizacaoId") Long organizacaoId);
}
