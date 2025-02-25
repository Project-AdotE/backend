package com.adote.api.core.usecases.organizacao.post;

import com.adote.api.core.entities.Organizacao;

public interface CreateOrganizacaoCase {

    Organizacao execute(Organizacao organizacao);

}
