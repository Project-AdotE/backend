package com.adote.api.infra.dtos.animal.request;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record AnimalPatchDTO(
        @Valid
        Optional<@NotBlank(message = "O nome não pode estar em branco")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres") String> nome,

        @Valid
        Optional<TipoAnimalEnum> tipo,

        @Valid
        Optional<SexoEnum> sexo,

        @Valid
        Optional<PorteEnum> porte,

        @Valid
        Optional<IdadeEnum> idade,

        @Valid
        Optional<Boolean> vacinado,

        @Valid
        Optional<Boolean> castrado,

        @Valid
        Optional<Boolean> vermifugado,

        @Valid
        Optional<Boolean> srd,

        Optional<Boolean> adotado,

        @Valid
        Optional<@NotBlank(message = "A descrição não pode estar em branco")
        @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres") String> descricao
) {}
