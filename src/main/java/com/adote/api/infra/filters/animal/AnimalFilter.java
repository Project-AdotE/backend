package com.adote.api.infra.filters.animal;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import lombok.Data;

@Data
public class AnimalFilter {
    private TipoAnimalEnum tipo;
    private IdadeEnum idade;
    private PorteEnum porte;
    private SexoEnum sexo;
    private Long organizacaoId;
}
