package com.adote.api.core.entities;

import com.adote.api.core.Enums.TipoChaveEnum;

public record ChavePix(
        Long id,
        TipoChaveEnum tipo,
        String chave
) {
}
