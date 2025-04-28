package com.adote.api.infra.dtos.formulario.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record FormularioRequestDTO(
        @NotNull(message = "O id do animal é obrigatório.")
        Long idAnimal,

        @NotNull(message = "O id da organização é obrigatório.")
        Long idOrganizacao,

        @NotBlank(message = "O nome do adotante é obrigatório.")
        @Size(max = 255, message = "O nome do adotante deve ter no máximo 255 caracteres.")
        String nomeAdotante,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail informado é inválido.")
        String email,

        @NotNull(message = "A idade não pode ser nula")
        @Min(value = 21, message = "Idade minima permitida é 21 anos")
        @Max(value = 99, message = "Idade máxima permitida é 99 anos")
        Long idade,

        @NotBlank(message = "O telefone é obrigatório.")
        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String telefone,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF informado é inválido.")
        String cpf,

        @NotNull(message = "As respostas devem ser preenchidas")
        List<RespostaRequestDTO> respostas
) {
}
