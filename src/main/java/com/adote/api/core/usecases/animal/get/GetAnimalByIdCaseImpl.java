package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;

import java.util.Optional;

public class GetAnimalByIdCaseImpl implements GetAnimalByIdCase {

    private final AnimalGateway animalGateway;

    public GetAnimalByIdCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public Optional<Animal> execute(Long id) {
        return animalGateway.getAnimalById(id);
    }
}
