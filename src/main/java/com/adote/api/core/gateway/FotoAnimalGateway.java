package com.adote.api.core.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface FotoAnimalGateway {

    FotoAnimal createFotoAnimal(FotoAnimal fotoAnimal);

    List<FotoAnimal> createMultipleFotos(List<FotoAnimal> fotos);

    List<FotoAnimal> getFotosByAnimalId(Long id);

    Optional<FotoAnimal> getFotoAnimalByUrl(String url);

}
