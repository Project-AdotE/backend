package com.adote.api.core.usecases.chavePix.patch;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.gateway.ChavePixGateway;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;

public class UpdateChaveByIdCaseImpl implements UpdateChaveByIdCase {

    private final ChavePixGateway gateway;

    public UpdateChaveByIdCaseImpl(final ChavePixGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public ChavePix execute(Long id, ChavePixUpdateDTO updateDTO) {
        return gateway.updateChaveById(id, updateDTO);
    }
}
