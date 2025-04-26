package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.gateway.OrganizacaoGateway;

public class GetOrganizacaoByEmailUseCaseImpl implements GetOrganizacaoByEmailUseCase {

    private final OrganizacaoGateway organizacaoGateway;

    public GetOrganizacaoByEmailUseCaseImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Organizacao execute(String email) {
        return organizacaoGateway.getOrganizacaoByEmail(email)
                .orElseThrow(() -> new OrganizacaoNotFoundException(email));
    }
}
