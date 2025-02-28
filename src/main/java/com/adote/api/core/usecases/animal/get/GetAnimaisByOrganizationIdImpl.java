package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetAnimaisByOrganizationIdImpl implements GetAnimaisByOrganizationId {

    private final AnimalGateway animalGateway;

    public GetAnimaisByOrganizationIdImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public Page<Animal> execute(Long id, Pageable pageable) {
        return animalGateway.getAnimaisByOrganizationId(id, pageable);
    }
}