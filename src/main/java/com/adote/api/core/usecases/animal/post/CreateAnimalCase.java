package com.adote.api.core.usecases.animal.post;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;

public interface CreateAnimalCase {

    Animal execute(AnimalRequestDTO animalRequestDTO);

}
