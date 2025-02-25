package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.OrganizacaoGateway;

import java.util.Optional;

public class GetOrganizacaoByIdImpl implements GetOrganizacaoById {

    private final OrganizacaoGateway organizacaoGateway;

    public GetOrganizacaoByIdImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Optional<Organizacao> execute(Long id) {
        return organizacaoGateway.getOrganizacaoById(id);
    }
}
