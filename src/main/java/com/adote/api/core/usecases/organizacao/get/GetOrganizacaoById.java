package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;

import java.util.Optional;

public interface GetOrganizacaoById {

    Optional<Organizacao> execute(Long id);

}
