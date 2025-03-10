package com.adote.api.infra.dtos.organizacao.request;

import com.adote.api.infra.dtos.enderecoOrganizacao.request.EnderecoRequestDTO;

public record OrganizacaoRequestDTO(
        String nome,
        String numero,
        String cnpj,
        EnderecoRequestDTO endereco,
        String email,
        String senha) {
}
