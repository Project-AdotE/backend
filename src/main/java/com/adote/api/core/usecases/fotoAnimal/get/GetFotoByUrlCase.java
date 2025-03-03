package com.adote.api.core.usecases.fotoAnimal.get;

import com.adote.api.core.entities.FotoAnimal;

import java.util.Optional;

public interface GetFotoByUrlCase {

    Optional<FotoAnimal> getFotoAnimalByUrl(String url);

}
