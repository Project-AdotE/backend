package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;

import java.util.List;

public class GetAnimaisByOrganizationIdImpl implements GetAnimaisByOrganizationId {

    private final AnimalGateway animalGateway;

    public GetAnimaisByOrganizationIdImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public List<Animal> execute(Long id) {
        return List.of();
    }
}
