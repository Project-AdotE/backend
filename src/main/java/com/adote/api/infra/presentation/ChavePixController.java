package com.adote.api.infra.presentation;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.usecases.chavePix.get.GetAllChavesCase;
import com.adote.api.core.usecases.chavePix.get.GetChavesByOrgIdCase;
import com.adote.api.core.usecases.chavePix.patch.UpdateChaveByIdCase;
import com.adote.api.core.usecases.chavePix.post.CreateChaveCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixResponseDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chavepix")
@RequiredArgsConstructor
@Tag(name = "Chave Pix", description = "Responsável pelo gerenciamento das chaves PIX das organizações")
public class ChavePixController {

    private final GetOrganizacaoById getOrganizacaoById;

    private final CreateChaveCase createChaveCase;
    private final GetAllChavesCase getAllChavesCase;
    private final GetChavesByOrgIdCase getChavesByOrgIdCase;
    private final UpdateChaveByIdCase updateChaveByIdCase;

    private final ChavePixMapper chavePixMapper;

    @GetMapping
    public ResponseEntity<List<ChavePixResponseDTO>> getAllChavePix() {
        List<ChavePix> chavePixList = getAllChavesCase.execute();
        return ResponseEntity.ok().body(chavePixList.stream()
                .map(chavePixMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/organizacao/{id}")
    public ResponseEntity<List<ChavePixSimplificadaDTO>> getChavesPixByOrganizacao(@PathVariable Long id) {
        List<ChavePix> chavesPix = getChavesByOrgIdCase.execute(id);
        List<ChavePixSimplificadaDTO> response = chavesPix.stream()
                .map(chavePix -> new ChavePixSimplificadaDTO(
                        chavePix.id(),
                        chavePix.tipo(),
                        chavePix.chave()))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ChavePixResponseDTO> createChave(@RequestBody ChavePixRequestDTO chavePixRequestDTO) {
        ChavePix newChave = createChaveCase.execute(chavePixRequestDTO);
        if (newChave == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chavePixMapper.toResponseDTO(newChave));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChavePixSimplificadaDTO> updateChavePix(
            @PathVariable Long id,
            @RequestBody ChavePixUpdateDTO updateDTO) {

        ChavePix updatedChavePix = updateChaveByIdCase.execute(id, updateDTO);
        if (updatedChavePix == null) {
            return ResponseEntity.badRequest().build();
        }
        ChavePixSimplificadaDTO responseDTO = new ChavePixSimplificadaDTO(
                updatedChavePix.id(),
                updatedChavePix.tipo(),
                updatedChavePix.chave()
        );

        return ResponseEntity.ok(responseDTO);
    }

}
