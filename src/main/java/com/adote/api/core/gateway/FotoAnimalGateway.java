package com.adote.api.core.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;

import java.util.List;

public interface FotoAnimalGateway {

    FotoAnimal createFotoAnimal(FotoAnimal fotoAnimal);

    List<FotoAnimal> createMultipleFotos(List<FotoAnimal> fotos);

}
