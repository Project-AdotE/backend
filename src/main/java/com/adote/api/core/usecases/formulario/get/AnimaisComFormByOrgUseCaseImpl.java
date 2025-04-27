package com.adote.api.core.usecases.formulario.get;

import com.adote.api.core.gateway.FormularioGateway;
import com.adote.api.infra.dtos.formulario.response.AnimalComFormResponseDTO;

import java.util.List;

public class AnimaisComFormByOrgUseCaseImpl implements AnimaisComFormByOrgUseCase {

    private final FormularioGateway formularioGateway;

    public AnimaisComFormByOrgUseCaseImpl(FormularioGateway formularioGateway) {
        this.formularioGateway = formularioGateway;
    }

    @Override
    public List<AnimalComFormResponseDTO> execute(Long organizacaoId) {
        return formularioGateway.getAnimalComFormByOrganizacaoId(organizacaoId);
    }
}
