package com.adote.api.core.usecases.formulario.post;

import com.adote.api.infra.dtos.formulario.request.MensagemRecusaDTO;

public interface RecusarFormularioUseCase {

    void execute(MensagemRecusaDTO mensagemRecusaDTO, Long id, Long tokenOrgId);

}
