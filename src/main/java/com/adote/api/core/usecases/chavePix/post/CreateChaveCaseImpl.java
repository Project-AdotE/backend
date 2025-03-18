package com.adote.api.core.usecases.chavePix.post;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.gateway.ChavePixGateway;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;

public class CreateChaveCaseImpl implements CreateChaveCase {

    private final ChavePixGateway gateway;

    public CreateChaveCaseImpl(final ChavePixGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public ChavePix execute(ChavePixRequestDTO chavePixRequestDTO) {
        return gateway.createChavePix(chavePixRequestDTO);
    }
}
