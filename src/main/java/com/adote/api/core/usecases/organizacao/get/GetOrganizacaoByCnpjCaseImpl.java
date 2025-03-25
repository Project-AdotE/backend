package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.gateway.OrganizacaoGateway;

public class GetOrganizacaoByCnpjCaseImpl implements GetOrganizacaoByCnpjCase {

    private final OrganizacaoGateway organizacaoGateway;

    public GetOrganizacaoByCnpjCaseImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Organizacao execute(String cnpj) {
        return organizacaoGateway.getOrganizacaoByCnpj(cnpj)
                .orElseThrow(() -> new OrganizacaoNotFoundException(cnpj));
    }
}
