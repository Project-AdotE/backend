package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.Enums.StatusFormularioEnum;
import com.adote.api.infra.persistence.entities.FormularioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularioRepository extends JpaRepository<FormularioEntity, Long> {
    List<FormularioEntity> findAllByOrganizacao_Id(Long organizacaoId);

    List<FormularioEntity> findByAnimal_IdAndStatusInOrderByStatusAscDataEnvioAsc(Long animalId, List<StatusFormularioEnum> statuses);

    boolean existsByEmailAndAnimal_Id(String email, Long animalId);

    boolean existsByCpfAndAnimal_Id(String cpf, Long animalId);

    List<FormularioEntity> findAllByAnimal_Id(Long animalId);
}
