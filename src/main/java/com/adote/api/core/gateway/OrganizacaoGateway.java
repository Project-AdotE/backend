package com.adote.api.core.gateway;

import com.adote.api.core.entities.Organizacao;

import java.util.Optional;

public interface OrganizacaoGateway {

    Organizacao createOrganizacao(Organizacao organizacao);
    Optional<Organizacao> getOrganizacaoById(Long id);
    void deleteOrganizacaoById(Long id);
}
