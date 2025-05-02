package com.adote.api.infra.presentation;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.usecases.animal.delete.DeleteAnimalByIdCase;
import com.adote.api.core.usecases.animal.get.GetAllAnimaisCase;
import com.adote.api.core.usecases.animal.get.GetAnimalByIdCase;
import com.adote.api.core.usecases.animal.patch.UpdateAnimalCase;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.dtos.animal.request.AnimalPatchDTO;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.dtos.page.response.PageResponseDTO;
import com.adote.api.infra.filters.animal.AnimalFilter;
import com.adote.api.infra.mappers.AnimalMapper;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
@Tag(name = "Animal", description = "Responsavel pelo gerenciamento de animais")
public class AnimalController {

    private final CreateAnimalCase createAnimalCase;
    private final UpdateAnimalCase updateAnimalCase;
    private final GetAllAnimaisCase getAllAnimaisCase;
    private final GetAnimalByIdCase getAnimalByIdCase;
    private final DeleteAnimalByIdCase deleteAnimalByIdCase;

    private final TokenService tokenService;

    private final AnimalMapper animalMapper;

    @GetMapping
    public ResponseEntity<PageResponseDTO<AnimalResponseDTO>> findAll(
            @RequestParam(required = false) TipoAnimalEnum tipo,
            @RequestParam(required = false) IdadeEnum idade,
            @RequestParam(required = false) PorteEnum porte,
            @RequestParam(required = false) SexoEnum sexo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cidade,
            @RequestParam(defaultValue = "0") int page) {

        AnimalFilter filter = new AnimalFilter();
        filter.setTipo(tipo);
        filter.setIdade(idade);
        filter.setPorte(porte);
        filter.setSexo(sexo);
        filter.setCidade(cidade);
        filter.setEstado(estado);

        Pageable pageable = PageRequest.of(page, 20);

        Page<Animal> animalPage = getAllAnimaisCase.execute(filter, pageable);

        List<AnimalResponseDTO> animalResponseDTOList = animalPage.getContent().stream()
                .map(animalMapper::toResponseDTO)
                .collect(Collectors.toList());

        PageResponseDTO<AnimalResponseDTO> response = new PageResponseDTO<>(
                animalResponseDTOList,
                animalPage.getNumber(),
                animalPage.getTotalElements(),
                animalPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> findAnimalById(@PathVariable Long id) {
        Animal animal = getAnimalByIdCase.execute(id);
        return ResponseEntity.ok(animalMapper.toResponseDTO(animal));
    }

    @GetMapping("/organizacao/{id}")
    public ResponseEntity<PageResponseDTO<AnimalResponseDTO>> getAnimaisByOrganizacao(
            @PathVariable Long id,
            @RequestParam(required = false) TipoAnimalEnum tipo,
            @RequestParam(required = false) IdadeEnum idade,
            @RequestParam(required = false) PorteEnum porte,
            @RequestParam(required = false) SexoEnum sexo,
            @RequestParam(defaultValue = "0") int page) {

        AnimalFilter filter = new AnimalFilter();
        filter.setTipo(tipo);
        filter.setIdade(idade);
        filter.setPorte(porte);
        filter.setSexo(sexo);
        filter.setOrganizacaoId(id);

        Pageable pageable = PageRequest.of(page, 20);

        Page<Animal> animalPage = getAllAnimaisCase.execute(filter, pageable);

        List<AnimalResponseDTO> animais = animalPage.getContent().stream()
                .map(animalMapper::toResponseDTO)
                .toList();

        PageResponseDTO<AnimalResponseDTO> response = new PageResponseDTO<>(
                animais,
                animalPage.getNumber(),
                animalPage.getTotalElements(),
                animalPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimalResponseDTO> createAnimal(
            @RequestPart("dados") AnimalRequestDTO requestDTO,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos) {

        Long tokenOrgId = tokenService.getOrganizacaoId();

        if (!tokenOrgId.equals(requestDTO.organizacao_id())) {
            throw new RuntimeException("Você não pode criar uma animal para a organização.");
        }

        Animal newAnimal = createAnimalCase.execute(requestDTO, fotos);

        return ResponseEntity.ok(animalMapper.toResponseDTO(newAnimal));
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimalResponseDTO> updateAnimal(
            @PathVariable Long id,
            @RequestPart(value = "dados", required = false) AnimalPatchDTO requestDTO,
            @RequestPart(value = "novasFotos", required = false) List<MultipartFile> novasFotos,
            @RequestPart(value = "fotosParaRemover", required = false) List<String> fotosParaRemover) {

        Long tokenOrgId = tokenService.getOrganizacaoId();

        Animal updatedAnimal = updateAnimalCase.execute(id, requestDTO, novasFotos, fotosParaRemover, tokenOrgId);

        return ResponseEntity.ok(animalMapper.toResponseDTO(updatedAnimal));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimalById(@PathVariable Long id) {
        Long tokenOrgId = tokenService.getOrganizacaoId();

        deleteAnimalByIdCase.execute(id, tokenOrgId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
