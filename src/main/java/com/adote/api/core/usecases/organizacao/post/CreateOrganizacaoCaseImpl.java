package com.adote.api.core.usecases.organizacao.post;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.OrganizacaoGateway;

public class CreateOrganizacaoCaseImpl implements CreateOrganizacaoCase {

    private final OrganizacaoGateway organizacaoGateway;

    public CreateOrganizacaoCaseImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Organizacao execute(Organizacao organizacao) {
        return organizacaoGateway.createOrganizacao(organizacao);
    }
}
