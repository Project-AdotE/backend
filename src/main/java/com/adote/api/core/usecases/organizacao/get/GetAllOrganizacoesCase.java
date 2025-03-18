package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.filters.organizacao.OrganizacaoFilter;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllOrganizacoesCase {

    Page<Organizacao> execute(OrganizacaoFilter filter, Pageable pageable);
}
