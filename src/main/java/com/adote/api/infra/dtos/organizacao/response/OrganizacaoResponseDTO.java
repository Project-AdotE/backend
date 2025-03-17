package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;

import java.util.List;
import java.util.Map;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoResponseDTO endereco,
        String email,
        Map<String, Object> animais,
        List<ChavePixSimplificadaDTO> chavesPix
) {
    public OrganizacaoResponseDTO(
            Long id,
            String nome,
            String numero,
            String cnpj,
            EnderecoResponseDTO endereco,
            String email) {
        this(id, nome, numero, cnpj, endereco, email, null, null);
    }

    // Novo construtor sem chavesPix
    public OrganizacaoResponseDTO(
            Long id,
            String nome,
            String numero,
            String cnpj,
            EnderecoResponseDTO endereco,
            String email,
            Map<String, Object> animais) {
        this(id, nome, numero, cnpj, endereco, email, animais, null);
    }
}
