package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.usecases.animal.delete.DeleteAnimalByIdCase;
import com.adote.api.core.usecases.animal.get.GetAllAnimaisCase;
import com.adote.api.core.usecases.animal.get.GetAnimaisByOrganizationId;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
@Tag(name = "Animal", description = "Responsavel pelo gerenciamento de animais")
public class AnimalController {

    private final CreateAnimalCase createAnimalCase;
    private final GetAnimaisByOrganizationId getAnimaisByOrganizationId;
    private final GetAllAnimaisCase getAllAnimaisCase;
    private final DeleteAnimalByIdCase deleteAnimalByIdCase;
    private final AnimalMapper animalMapper;

    @Operation(summary = "Busca de animais", description = "Busca por todos os animais ou por organização",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna lista de animais")
    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(required = false) Long orgId,
            @RequestParam(defaultValue = "0") int page) {

        // Criar o Pageable com PageRequest
        Pageable pageable = PageRequest.of(page, 5);

        Page<Animal> animalPage;
        if (orgId == null) {
            animalPage = getAllAnimaisCase.execute(pageable);
        } else {
            animalPage = getAnimaisByOrganizationId.execute(orgId, pageable);
        }

        // Mapear os resultados para DTOs
        List<AnimalResponseDTO> animalResponseDTOList = animalPage.stream()
                .map(animalMapper::toResponseDTO)
                .collect(Collectors.toList());

        // Criar a resposta
        Map<String, Object> response = new HashMap<>();
        response.put("animals", animalResponseDTOList);
        response.put("currentPage", animalPage.getNumber());
        response.put("totalItems", animalPage.getTotalElements());
        response.put("totalPages", animalPage.getTotalPages());

        return ResponseEntity.ok().body(response);
    }
//    @GetMapping("/find/all")
//    public ResponseEntity<List<AnimalResponseDTO>> findAll(
//            @RequestParam(required = false) Long orgId) {
//
//        if(orgId == null) {
//            List<Animal> animalList = getAllAnimaisCase.execute();
//            return ResponseEntity.ok().body(animalList.stream()
//                    .map(animalMapper::toResponseDTO)
//                    .toList());
//        }
//
//        List<Animal> animalList = getAnimaisByOrganizationId.execute(orgId);
//        return ResponseEntity.ok().body(animalList.stream()
//                .map(animalMapper::toResponseDTO)
//                .toList());
//
//    }


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimalResponseDTO> createAnimal(
            @RequestPart("dados") AnimalRequestDTO requestDTO,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos) {

        Animal newAnimal = createAnimalCase.execute(requestDTO, fotos);

        if (newAnimal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(animalMapper.toResponseDTO(newAnimal));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAnimalById(@RequestParam Long animalId) {
        deleteAnimalByIdCase.execute(animalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
