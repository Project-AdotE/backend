package com.adote.api.core.usecases.fotoAnimal.post;

import com.adote.api.core.entities.FotoAnimal;

public interface CreateFotoAnimalCase {

    FotoAnimal execute(FotoAnimal fotoAnimal);

}
