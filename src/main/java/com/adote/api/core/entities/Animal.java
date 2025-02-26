package com.adote.api.core.entities;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;

public record Animal(Long id,
                     String nome,
                     SexoEnum sexo,
                     PorteEnum porte,
                     Boolean vacinado,
                     Organizacao organizacao) {
}
