package com.adote.api.infra.dtos.animal.request;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Organizacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnimalRequestDTO(
        @NotBlank(message = "O nome do animal é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotNull(message = "O tipo do animal é obrigatório")
        TipoAnimalEnum tipo,

        @NotNull(message = "O sexo do animal é obrigatório")
        SexoEnum sexo,

        @NotNull(message = "O porte do animal é obrigatório")
        PorteEnum porte,

        @NotNull(message = "A idade do animal é obrigatória")
        IdadeEnum idade,

        @NotNull(message = "É necessário informar se o animal está vacinado")
        Boolean vacinado,

        @NotNull(message = "É necessário informar se o animal está castrado")
        Boolean castrado,

        @NotNull(message = "É necessário informar se o animal está vermifugado")
        Boolean vermifugado,

        @NotNull(message = "É necessário informar se o animal é SRD")
        Boolean srd,

        @NotNull(message = "É necessário informar se o animal esta adotado")
        Boolean adotado,

        @NotBlank(message = "A descrição do animal é obrigatória")
        @Size(min = 300, max = 500, message = "A descrição deve ter entre 300 e 500 caracteres")
        String descricao,

        @NotNull(message = "O ID da organização é obrigatório")
        Long organizacao_id
) {}
