package com.adote.api.core.usecases.fotoAnimal.get;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.gateway.FotoAnimalGateway;

import java.util.List;

public class GetFotoByAnimalIdCaseImpl implements GetFotoByAnimalIdCase {

    private final FotoAnimalGateway fotoAnimalGateway;

    public GetFotoByAnimalIdCaseImpl(final FotoAnimalGateway fotoAnimalGateway) {
        this.fotoAnimalGateway = fotoAnimalGateway;
    }

    @Override
    public List<FotoAnimal> execute(Long id) {
        return fotoAnimalGateway.getFotosByAnimalId(id);
    }
}
