package com.adote.api.infra.dtos.chavePix.response;

import com.adote.api.infra.dtos.organizacao.response.OrganizacaoBaseDTO;

public record ChavePixResponseDTO(
        Long id,
        String tipo,
        String chave,
        OrganizacaoBaseDTO organizacao
) {
}
