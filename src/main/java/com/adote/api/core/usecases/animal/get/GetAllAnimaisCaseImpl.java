package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;

import java.util.List;

public class GetAllAnimaisCaseImpl implements GetAllAnimaisCase {

    private final AnimalGateway animalGateway;

    public GetAllAnimaisCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public List<Animal> execute() {
        return animalGateway.getAllAnimaisCase();
    }
}
