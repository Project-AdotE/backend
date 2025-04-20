package com.adote.api.infra.dtos.passwordToken.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetSenhaRequestDTO(
        @Email @NotBlank(message = "O e-mail não pode ser vazio.") String email
) {
}
