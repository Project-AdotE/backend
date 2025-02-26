package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;

import java.util.Optional;

public interface GetAnimalByIdCase {

    Optional<Animal> execute(Long id);

}
