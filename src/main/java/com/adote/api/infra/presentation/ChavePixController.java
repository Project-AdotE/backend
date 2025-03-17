package com.adote.api.infra.presentation;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.usecases.chavePix.get.GetAllChavesCase;
import com.adote.api.core.usecases.chavePix.post.CreateChaveCase;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixResponseDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chavepix")
@RequiredArgsConstructor
public class ChavePixController {

    private final CreateChaveCase createChaveCase;
    private final GetAllChavesCase getAllChavesCase;

    private final ChavePixMapper chavePixMapper;

    @GetMapping
    public ResponseEntity<List<ChavePixResponseDTO>> getAllChavePix() {
        List<ChavePix> chavePixList = getAllChavesCase.execute();
        return ResponseEntity.ok().body(chavePixList.stream()
                .map(chavePixMapper::toResponseDTO)
                .toList());
    }


    @PostMapping
    public ResponseEntity<ChavePixResponseDTO> createChave(@RequestBody ChavePixRequestDTO chavePixRequestDTO) {
        ChavePix newChave = createChaveCase.execute(chavePixRequestDTO);
        if (newChave == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chavePixMapper.toResponseDTO(newChave));
    }

}
