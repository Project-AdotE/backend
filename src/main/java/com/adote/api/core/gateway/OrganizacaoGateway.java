package com.adote.api.core.gateway;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.filters.organizacao.OrganizacaoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrganizacaoGateway {

    Organizacao createOrganizacao(Organizacao organizacao);

    Optional<Organizacao> getOrganizacaoById(Long id);

    Optional<Organizacao> getOrganizacaoByCnpj(String cnpj);

    Optional<Organizacao> getOrganizacaoByEmail(String email);

    void deleteOrganizacaoById(Long id);

    Page<Organizacao> getAllorganizacoes(OrganizacaoFilter filter, Pageable pageable);
}