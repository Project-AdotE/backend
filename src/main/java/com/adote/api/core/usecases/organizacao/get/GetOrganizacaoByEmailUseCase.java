package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;

public interface GetOrganizacaoByEmailUseCase {

    Organizacao execute(String email);

}
