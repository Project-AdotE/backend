package com.adote.api.core.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnimalGateway {

    Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos);

    List<Animal> getAnimaisByOrganizationId(Long id);

    List<Animal> getAllAnimaisCase();

    Optional<Animal> getAnimalById(Long id);

}
