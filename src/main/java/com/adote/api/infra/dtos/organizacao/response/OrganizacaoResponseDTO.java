package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.animal.response.AnimaisPaginadosDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;

import java.util.List;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoResponseDTO endereco,
        String email,
        AnimaisPaginadosDTO animais,
        List<ChavePixSimplificadaDTO> chavesPix) {
}