package com.adote.api.infra.presentation;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.usecases.chavePix.patch.UpdateChaveByIdCase;
import com.adote.api.core.usecases.chavePix.post.CreateChaveCase;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chavepix")
@RequiredArgsConstructor
@Tag(name = "Chave Pix", description = "Responsável pelo gerenciamento das chaves PIX das organizações")
public class ChavePixController {

    private final CreateChaveCase createChaveCase;
    private final UpdateChaveByIdCase updateChaveByIdCase;

    private final TokenService tokenService;

    private final ChavePixMapper chavePixMapper;

    @PostMapping
    public ResponseEntity<ChavePixSimplificadaDTO> createChave(@RequestBody ChavePixRequestDTO chavePixRequestDTO) {
        Long tokenOrgId = tokenService.getOrganizacaoId();

        if (!tokenOrgId.equals(chavePixRequestDTO.organizacaoId())) {
            throw new RuntimeException("Você não pode criar chave para outra organização.");
        }

        ChavePix newChave = createChaveCase.execute(chavePixRequestDTO);
        return ResponseEntity.ok(chavePixMapper.toSimplificadaDTO(newChave));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChavePixSimplificadaDTO> updateChavePix(
            @PathVariable Long id,
            @RequestBody ChavePixUpdateDTO updateDTO) {

        Long tokenOrgId = tokenService.getOrganizacaoId();

        ChavePix updatedChavePix = updateChaveByIdCase.execute(id, tokenOrgId, updateDTO);

        ChavePixSimplificadaDTO responseDTO = new ChavePixSimplificadaDTO(
                updatedChavePix.id(),
                updatedChavePix.tipo(),
                updatedChavePix.chave()
        );

        return ResponseEntity.ok(responseDTO);
    }

}
