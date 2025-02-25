package com.adote.api.core.usecases.organizacao.delete;

import com.adote.api.core.gateway.OrganizacaoGateway;

public class DeleteOrganizacaoByIdImpl implements DeleteOrganizacaoById {

    private final OrganizacaoGateway organizacaoGateway;

    public DeleteOrganizacaoByIdImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public void execute(Long id) {
        organizacaoGateway.deleteOrganizacaoById(id);
    }
}
