package com.adote.api.infra.dtos.organizacao.request;

import com.adote.api.infra.dtos.enderecoOrganizacao.request.EnderecoOrganizacaoRequestDTO;

public record OrganizacaoRequestDTO(
        String nome,
        String numero,
        String cnpj,
        EnderecoOrganizacaoRequestDTO endereco,
        String email,
        String senha) {
}
