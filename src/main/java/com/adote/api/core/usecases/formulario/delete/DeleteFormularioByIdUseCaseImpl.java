package com.adote.api.core.usecases.formulario.delete;

import com.adote.api.core.gateway.FormularioGateway;

public class DeleteFormularioByIdUseCaseImpl implements DeleteFormularioByIdUseCase {

    private final FormularioGateway formularioGateway;

    public DeleteFormularioByIdUseCaseImpl(FormularioGateway formularioGateway){
        this.formularioGateway = formularioGateway;
    }

    @Override
    public void execute(Long id, Long tokenOrgId) {
        formularioGateway.deleteFormularioById(id, tokenOrgId);
    }
}
