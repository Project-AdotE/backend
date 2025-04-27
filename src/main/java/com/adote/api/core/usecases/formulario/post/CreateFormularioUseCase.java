package com.adote.api.core.usecases.formulario.post;

import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;

public interface CreateFormularioUseCase {

    void execute(FormularioRequestDTO formularioRequestDTO);

}
