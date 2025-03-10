package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.OrganizacaoGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetAllOrganizacoesCaseImpl implements GetAllOrganizacoesCase {

    private final OrganizacaoGateway organizacaoGateway;

    public GetAllOrganizacoesCaseImpl(OrganizacaoGateway organizacaoGateway) {
        this.organizacaoGateway = organizacaoGateway;
    }

    @Override
    public Page<Organizacao> execute(Pageable pageable) {
        return organizacaoGateway.getAllorganizacoes(pageable);
    }
}
