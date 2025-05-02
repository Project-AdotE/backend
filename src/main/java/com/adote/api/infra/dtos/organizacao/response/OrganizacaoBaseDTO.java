package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;

import java.util.List;

public record OrganizacaoBaseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoResponseDTO endereco,
        List<ChavePixSimplificadaDTO> chavesPix,
        String email,
        Long animaisAdotados) {
}
