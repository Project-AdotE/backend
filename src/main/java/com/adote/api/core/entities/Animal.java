package com.adote.api.core.entities;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;

import java.time.LocalDateTime;
import java.util.List;

public record Animal(Long id,
                     String nome,
                     TipoAnimalEnum tipo,
                     SexoEnum sexo,
                     PorteEnum porte,
                     IdadeEnum idade,
                     Boolean vacinado,
                     Boolean castrado,
                     Boolean vermifugado,
                     Boolean srd,
                     Boolean adotado,
                     String descricao,
                     Organizacao organizacao,
                     List<FotoAnimal> fotos,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
}
