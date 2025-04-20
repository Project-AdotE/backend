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
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//        private Long id;
//        private String nome;
//        private String numero;
//        private String cnpj;
//        private EnderecoResponseDTO endereco;
//        private String email;
//        private AnimaisPaginadosDTO animais;
//        private List<ChavePixSimplificadaDTO> chavesPix;
//
//        public Builder id(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        public OrganizacaoResponseDTO build() {
//            return new OrganizacaoResponseDTO(id, nome, numero, cnpj, endereco, email, animais, chavesPix);
//        }
//    }
}