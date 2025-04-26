package com.adote.api.core.gateway;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;

import java.util.List;

public interface ChavePixGateway {

    ChavePix createChavePix(ChavePixRequestDTO chavePixRequestDTO);

    ChavePix updateChaveById(Long id, Long tokenOrgId, ChavePixUpdateDTO updateDTO);

}
