package com.adote.api.core.usecases.chavePix.post;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;

public interface CreateChaveCase {

    ChavePix execute(ChavePixRequestDTO chavePixRequestDTO);

}
