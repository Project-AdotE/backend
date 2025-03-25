package com.adote.api.infra.dtos.chavePix.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChavePixUpdateDTO(

        @NotNull(message = "O tipo da chave PIX é obrigatório")
        @Pattern(regexp = "CPF|CNPJ|EMAIL|TELEFONE|ALEATORIA", message = "Tipo inválido. Os valores permitidos são: CPF, CNPJ, EMAIL, TELEFONE ou ALEATORIA")
        String tipo,

        @NotNull(message = "A chave PIX é obrigatória")
        @Size(min = 1, max = 100, message = "A chave PIX deve ter entre 1 e 100 caracteres.")
        String chave
) {
}
