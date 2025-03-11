package com.adote.api.core.gateway;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnimalGateway {

    Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos);

    Page<Animal> getAllAnimaisCase(
            TipoAnimalEnum tipo,
            IdadeEnum idade,
            PorteEnum porte,
            SexoEnum sexo,
            Long orgId,
            Pageable pageable
    );

    Optional<Animal> getAnimalById(Long id);

    Animal updateAnimal(Long id, AnimalRequestDTO animalRequestDTO, List<MultipartFile> novasFotos, List<String> fotosParaRemover);

    void deleteAnimalById(Long id);

}
