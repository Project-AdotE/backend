package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.usecases.animal.get.GetAllAnimaisCase;
import com.adote.api.core.usecases.animal.get.GetAnimaisByOrganizationId;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final CreateAnimalCase createAnimalCase;
    private final GetAnimaisByOrganizationId getAnimaisByOrganizationId;
    private final GetAllAnimaisCase getAllAnimaisCase;
    private final AnimalMapper animalMapper;

    @GetMapping("/find/all")
    public ResponseEntity<List<AnimalResponseDTO>> findAll(
            @RequestParam(required = false) Long orgId) {

        if(orgId == null) {
            List<Animal> animalList = getAllAnimaisCase.execute();
            return ResponseEntity.ok().body(animalList.stream()
                    .map(animalMapper::toResponseDTO)
                    .toList());
        }

        List<Animal> animalList = getAnimaisByOrganizationId.execute(orgId);
        return ResponseEntity.ok().body(animalList.stream()
                .map(animalMapper::toResponseDTO)
                .toList());

    }

    @PostMapping("/create")
    public ResponseEntity<AnimalResponseDTO> createAnimal(@RequestBody AnimalRequestDTO requestDTO) {
        Animal newAnimal = createAnimalCase.execute(requestDTO);
        if (newAnimal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(animalMapper.toResponseDTO(newAnimal));
    }

    

}
