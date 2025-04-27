package com.adote.api.core.usecases.formulario.get;

import com.adote.api.infra.dtos.formulario.response.AnimalComFormResponseDTO;

import java.util.List;

public interface AnimaisComFormByOrgUseCase {

    List<AnimalComFormResponseDTO> execute(Long organizacaoId);

}
