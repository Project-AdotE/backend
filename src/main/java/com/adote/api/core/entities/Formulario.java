package com.adote.api.core.entities;

import com.adote.api.core.Enums.StatusFormularioEnum;

import java.time.LocalDateTime;

public record Formulario(
        Long id,
        Animal animal,
        Organizacao organizacao,
        String nomeAdotante,
        String email,
        String telefone,
        String cpf,
        StatusFormularioEnum status,
        LocalDateTime dataEnvio,
        LocalDateTime dataResposta
) {
}
