package com.adote.api.core.usecases.fotoAnimal.get;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.gateway.FotoAnimalGateway;

import java.util.Optional;

public class GetFotoByUrlCaseImpl implements GetFotoByUrlCase {

    private final FotoAnimalGateway fotoAnimalGateway;

    public GetFotoByUrlCaseImpl(final FotoAnimalGateway fotoAnimalGateway) {
        this.fotoAnimalGateway = fotoAnimalGateway;
    }

    @Override
    public Optional<FotoAnimal> getFotoAnimalByUrl(String url) {
        return fotoAnimalGateway.getFotoAnimalByUrl(url);
    }
}
