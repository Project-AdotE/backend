package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GetAllAnimaisCaseImpl implements GetAllAnimaisCase {

    private final AnimalGateway animalGateway;

    public GetAllAnimaisCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public Page<Animal> execute(TipoAnimalEnum tipo, IdadeEnum idade, PorteEnum porte, SexoEnum sexo, Long orgId, Pageable pageable) {
        return animalGateway.getAllAnimaisCase(tipo, idade, porte, sexo, orgId, pageable);
    }
}
