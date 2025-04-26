package com.adote.api.core.usecases.chavePix.patch;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;

public interface UpdateChaveByIdCase {

    ChavePix execute(Long id, Long tokenOrgId, ChavePixUpdateDTO updateDTO);

}
