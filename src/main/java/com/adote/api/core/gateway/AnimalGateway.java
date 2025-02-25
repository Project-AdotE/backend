package com.adote.api.core.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;

public interface AnimalGateway {

    Animal createAnimal(AnimalRequestDTO animalRequestDTO);

}
