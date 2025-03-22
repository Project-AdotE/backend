package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.gateway.OrganizacaoGateway;

public class GetOrganizacaoByIdImpl implements GetOrganizacaoById {

    private final OrganizacaoGateway organizacaoGateway;

    public GetOrganizacaoByIdImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Organizacao execute(Long id) {
        return organizacaoGateway.getOrganizacaoById(id)
                .orElseThrow(() -> new OrganizacaoNotFoundException(id.toString()));
    }
}
