package com.adote.api.core.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnimalGateway {

    Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos);

    Page<Animal> getAnimaisByOrganizationId(Long id, Pageable pageable);

    Page<Animal> getAllAnimaisCase(Pageable pageable);

    Optional<Animal> getAnimalById(Long id);

    void deleteAnimalById(Long id);

}
