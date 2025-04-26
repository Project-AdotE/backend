package com.adote.api.core.gateway;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalPatchDTO;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.filters.animal.AnimalFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnimalGateway {

    Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos);

    Page<Animal> getAllAnimais(AnimalFilter filter, Pageable pageable);

    Optional<Animal> getAnimalById(Long id);

    Animal updateAnimal(Long id, AnimalPatchDTO animalRequestDTO, List<MultipartFile> novasFotos, List<String> fotosParaRemover, Long tokenOrgId);

    void deleteAnimalById(Long id, Long tokenOrgId);

}
