package com.adote.api.core.usecases.formulario.post;

import com.adote.api.core.gateway.FormularioGateway;

public class AceitarFormularioByIdUseCaseImpl implements AceitarFormularioByIdUseCase {

    private final FormularioGateway formularioGateway;

    public AceitarFormularioByIdUseCaseImpl(FormularioGateway formularioGateway) {
        this.formularioGateway = formularioGateway;
    }

    @Override
    public void execute(Long id, Long tokenOrgId) {
        formularioGateway.aceitarFormularioById(id, tokenOrgId);
    }
}
