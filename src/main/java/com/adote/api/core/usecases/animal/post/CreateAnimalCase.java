package com.adote.api.core.usecases.animal.post;

import com.adote.api.core.entities.Animal;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateAnimalCase {

    Animal execute(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos);

}
