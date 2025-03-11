package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;

import java.util.Map;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoResponseDTO endereco,
        String email,
        Map<String, Object> animais) {

    public OrganizacaoResponseDTO(
            Long id,
            String nome,
            String numero,
            String cnpj,
            EnderecoResponseDTO endereco,
            String email) {
        this(id, nome, numero, cnpj, endereco, email, null);
    }


}
