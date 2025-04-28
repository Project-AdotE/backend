package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.FormularioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularioRepository extends JpaRepository<FormularioEntity, Long> {
    List<FormularioEntity> findByEmailAndAnimal_Id(String email, Long animalId);

    List<FormularioEntity> findByCpfAndAnimal_Id(String cpf, Long animalId);

    List<FormularioEntity> findAllByOrganizacao_Id(Long organizacaoId);

    List<FormularioEntity> findAllByAnimal_Id(Long animalId);

    List<FormularioEntity> findAllByAnimal_IdOrderByDataEnvioAsc(Long animalId);
}
