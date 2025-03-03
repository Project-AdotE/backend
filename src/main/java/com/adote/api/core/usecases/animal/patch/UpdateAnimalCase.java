package com.adote.api.core.usecases.animal.patch;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdateAnimalCase {
    Animal execute(Long id, AnimalRequestDTO animalRequestDTO, List<MultipartFile> novasFotos, List<String> fotosParaRemover);
}
