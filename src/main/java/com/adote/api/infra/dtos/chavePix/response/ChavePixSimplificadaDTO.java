package com.adote.api.infra.dtos.chavePix.response;

import com.adote.api.core.Enums.TipoChaveEnum;

public record ChavePixSimplificadaDTO(
        Long id,
        TipoChaveEnum tipo,
        String chave
) {
}
