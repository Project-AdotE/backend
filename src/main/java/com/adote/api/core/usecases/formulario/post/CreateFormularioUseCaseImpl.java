package com.adote.api.core.usecases.formulario.post;

import com.adote.api.core.gateway.FormularioGateway;
import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;

public class CreateFormularioUseCaseImpl implements CreateFormularioUseCase {

    private final FormularioGateway formularioGateway;

    public CreateFormularioUseCaseImpl(FormularioGateway formularioGateway) {
        this.formularioGateway = formularioGateway;
    }

    @Override
    public void execute(FormularioRequestDTO formularioRequestDTO) {
        formularioGateway.criarFormulario(formularioRequestDTO);
    }
}
