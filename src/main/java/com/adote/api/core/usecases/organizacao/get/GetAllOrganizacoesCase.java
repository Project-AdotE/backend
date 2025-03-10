package com.adote.api.core.usecases.organizacao.get;

import com.adote.api.core.entities.Organizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllOrganizacoesCase {

    Page<Organizacao> execute(Pageable pageable);

}
