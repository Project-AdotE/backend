package com.adote.api.core.usecases.fotoAnimal.post;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.gateway.FotoAnimalGateway;

public class CreateFotoAnimalCaseImpl implements CreateFotoAnimalCase {

    private final FotoAnimalGateway fotoAnimalGateway;

    public CreateFotoAnimalCaseImpl(final FotoAnimalGateway fotoAnimalGateway) {
        this.fotoAnimalGateway = fotoAnimalGateway;
    }


    @Override
    public FotoAnimal execute(FotoAnimal fotoAnimal) {
        return fotoAnimalGateway.createFotoAnimal(fotoAnimal);
    }
}
