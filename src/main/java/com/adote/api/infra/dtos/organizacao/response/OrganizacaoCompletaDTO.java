package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;

import java.util.List;

public record OrganizacaoCompletaDTO(
        OrganizacaoComAnimaisDTO comAnimais,
        List<ChavePixSimplificadaDTO> chavesPix) {
}
