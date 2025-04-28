package com.adote.api.infra.dtos.formulario.response;

import com.adote.api.core.Enums.StatusFormularioEnum;

import java.time.LocalDateTime;
import java.util.List;

public record FormularioResponseDTO(
        Long id,
        StatusFormularioEnum status,
        String nomeAdotante,
        String email,
        Long idade,
        String telefone,
        String cpf,
        String dataEnvio,
        List<RespostaResponseDTO> respostas
) {}
