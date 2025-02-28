package com.adote.api.core.usecases.animal.get;

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
    public Page<Animal> execute(Pageable pageable) {
        return animalGateway.getAllAnimaisCase(pageable);
    }
}
