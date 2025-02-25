package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final CreateAnimalCase createAnimalCase;
    private final AnimalMapper animalMapper;

    @PostMapping("/create")
    public ResponseEntity<AnimalResponseDTO> createAnimal(@RequestBody AnimalRequestDTO requestDTO) {
        Animal newAnimal = createAnimalCase.execute(requestDTO);
        if (newAnimal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(animalMapper.toResponseDTO(newAnimal));
    }

    

}
