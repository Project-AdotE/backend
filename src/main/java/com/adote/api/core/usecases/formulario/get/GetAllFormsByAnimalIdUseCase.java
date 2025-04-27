package com.adote.api.core.usecases.formulario.get;

import com.adote.api.core.entities.Formulario;
import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllFormsByAnimalIdUseCase {

    List<FormularioResponseDTO> execute(Long animalId, Long tokenOrgId);

}
