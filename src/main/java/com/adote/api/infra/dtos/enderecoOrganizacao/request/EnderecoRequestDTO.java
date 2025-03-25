package com.adote.api.infra.dtos.enderecoOrganizacao.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(

        @NotNull(message = "CEP é obrigatório")
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido. Use o formato 00000-000")
        String cep,

        @NotNull(message = "Rua é obrigatória")
        @Size(min = 1, max = 100, message = "Tamanho da rua deve estar entre 1 e 100 caracteres.")
        String rua,

        @NotNull(message = "Número é obrigatório")
        @Size(min = 1, max = 10, message = "Tamanho do número deve estar entre 1 e 10 caracteres.")
        String numero,

        @NotNull(message = "Cidade é obrigatória")
        @Size(min = 1, max = 50, message = "Tamanho da cidade deve estar entre 1 e 50 caracteres.")
        String cidade,

        @NotNull(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "O estado deve ser a sigla de dois caracteres (ex: SP, RJ, MG).")
        String estado
) {
}
