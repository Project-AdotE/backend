package com.adote.api.core.usecases.formulario.post;

import com.adote.api.core.gateway.FormularioGateway;
import com.adote.api.infra.dtos.formulario.request.MensagemRecusaDTO;

public class RecusarFormularioUseCaseImpl implements RecusarFormularioUseCase {

    private final FormularioGateway formularioGateway;

    public RecusarFormularioUseCaseImpl(FormularioGateway formularioGateway) {
        this.formularioGateway = formularioGateway;
    }

    @Override
    public void execute(MensagemRecusaDTO mensagemRecusaDTO, Long id, Long tokenOrgId) {
        formularioGateway.recusarFormularioById(mensagemRecusaDTO, id, tokenOrgId);
    }
}
