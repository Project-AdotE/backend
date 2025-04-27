package com.adote.api.core.usecases.formulario.get;

import com.adote.api.core.entities.Formulario;
import com.adote.api.core.gateway.FormularioGateway;
import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GetAllFormsByAnimalIdUseCaseImpl implements GetAllFormsByAnimalIdUseCase {

    private final FormularioGateway formularioGateway;

    public GetAllFormsByAnimalIdUseCaseImpl(FormularioGateway formularioGateway) {
        this.formularioGateway = formularioGateway;
    }

    @Override
    public List<FormularioResponseDTO> execute(Long id, Long tokenOrgId) {
        return formularioGateway.getFormulariosByAnimalId(id, tokenOrgId);
    }
}
