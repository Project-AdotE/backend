package com.adote.api.core.usecases.fotoAnimal.post;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.gateway.FotoAnimalGateway;

import java.util.List;

public class CreateMultipleFotosCaseImpl implements CreateMultipleFotosCase {

    private final FotoAnimalGateway fotoAnimalGateway;

    public CreateMultipleFotosCaseImpl(final FotoAnimalGateway fotoAnimalGateway) {
        this.fotoAnimalGateway = fotoAnimalGateway;
    }

    @Override
    public List<FotoAnimal> execute(List<FotoAnimal> fotos) {
        return fotoAnimalGateway.createMultipleFotos(fotos);
    }
}
