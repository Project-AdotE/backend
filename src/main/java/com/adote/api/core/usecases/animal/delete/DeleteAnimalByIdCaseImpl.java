package com.adote.api.core.usecases.animal.delete;

import com.adote.api.core.gateway.AnimalGateway;

public class DeleteAnimalByIdCaseImpl implements DeleteAnimalByIdCase {

    private final AnimalGateway animalGateway;

    public DeleteAnimalByIdCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public void execute(Long id) {
        animalGateway.deleteAnimalById(id);
    }
}
