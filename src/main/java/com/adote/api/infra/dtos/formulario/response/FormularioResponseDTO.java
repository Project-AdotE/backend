package com.adote.api.infra.dtos.formulario.response;

import java.time.LocalDateTime;
import java.util.List;

public record FormularioResponseDTO(
        Long id,
        String nomeAdotante,
        String email,
        Long idade,
        String telefone,
        String cpf,
        LocalDateTime dataEnvio,
        List<RespostaResponseDTO> respostas
) {}
