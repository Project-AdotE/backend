package com.adote.api.core.usecases.animal.patch;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UpdateAnimalCaseImpl implements UpdateAnimalCase {

    private final AnimalGateway animalGateway;

    public UpdateAnimalCaseImpl(final AnimalGateway animalGateway) {
        this.animalGateway = animalGateway;
    }

    @Override
    public Animal execute(Long id, AnimalRequestDTO animalRequestDTO, List<MultipartFile> novasFotos, List<String> fotosParaRemover) {
        return animalGateway.updateAnimal(id, animalRequestDTO, novasFotos, fotosParaRemover);
    }
}
