package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {

    Page<AnimalEntity> findAllByOrganizacao_IdOrderByCreatedAtDesc(Long organizacaoId, Pageable pageable);
    Page<AnimalEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<AnimalEntity> findById(Long id);

    void deleteById(Long id);

    @Query("SELECT a FROM AnimalEntity a " +
            "WHERE (:tipo IS NULL OR a.tipo = :tipo) " +
            "AND (:idade IS NULL OR a.idade = :idade) " +
            "AND (:porte IS NULL OR a.porte = :porte) " +
            "AND (:sexo IS NULL OR a.sexo = :sexo) " +
            "ORDER BY a.createdAt DESC")
    Page<AnimalEntity> findAllWithFilters(
            @Param("tipo") TipoAnimalEnum tipo,
            @Param("idade") IdadeEnum idade,
            @Param("porte") PorteEnum porte,
            @Param("sexo") SexoEnum sexo,
            Pageable pageable
    );

}
