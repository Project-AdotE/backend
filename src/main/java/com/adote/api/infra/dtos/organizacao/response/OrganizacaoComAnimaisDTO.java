package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.dtos.page.response.PageResponseDTO;

public record OrganizacaoComAnimaisDTO(
        OrganizacaoBaseDTO base,
        PageResponseDTO<AnimalResponseDTO> animais) {
}