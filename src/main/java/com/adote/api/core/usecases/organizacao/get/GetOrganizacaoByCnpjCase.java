package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;

public interface GetOrganizacaoByCnpjCase {

    Organizacao execute(String cnpj);

}
