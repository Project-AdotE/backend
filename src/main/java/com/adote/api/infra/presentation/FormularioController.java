package com.adote.api.infra.presentation;

import com.adote.api.core.exceptions.auth.UnauthorizedAccessException;
import com.adote.api.core.usecases.formulario.delete.DeleteFormularioByIdUseCase;
import com.adote.api.core.usecases.formulario.get.AnimaisComFormByOrgUseCase;
import com.adote.api.core.usecases.formulario.get.GetAllFormsByAnimalIdUseCase;
import com.adote.api.core.usecases.formulario.post.AceitarFormularioByIdUseCase;
import com.adote.api.core.usecases.formulario.post.CreateFormularioUseCase;
import com.adote.api.core.usecases.formulario.post.RecusarFormularioUseCase;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;
import com.adote.api.infra.dtos.formulario.request.MensagemRecusaDTO;
import com.adote.api.infra.dtos.formulario.response.AnimalComFormResponseDTO;
import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;
import com.adote.api.infra.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formulario")
@RequiredArgsConstructor
public class FormularioController {

    private final AceitarFormularioByIdUseCase aceitarFormularioByIdUseCase;
    private final GetAllFormsByAnimalIdUseCase getAllFormsByAnimalIdUseCase;
    private final RecusarFormularioUseCase recusarFormularioUseCase;
    private final AnimaisComFormByOrgUseCase animaisComFormByOrgUseCase;
    private final DeleteFormularioByIdUseCase deleteFormularioByIdUseCase;

    private final CreateFormularioUseCase createFormularioUseCase;

    private final TokenService tokenService;
    private final RateLimiterService rateLimiterService;

    @GetMapping("/animal/{id}")
    public ResponseEntity<List<FormularioResponseDTO>> findAllByAnimalId(@PathVariable Long id) {
        Long tokenOrgId = tokenService.getOrganizacaoId();
        List<FormularioResponseDTO> formularioList = getAllFormsByAnimalIdUseCase.execute(id, tokenOrgId);
        return ResponseEntity.ok(formularioList);
    }

    @GetMapping("/organizacao/{organizacaoId}/animais")
    public List<AnimalComFormResponseDTO> getAnimaisComFormularios(@PathVariable Long organizacaoId) {
        Long tokenOrgId = tokenService.getOrganizacaoId();
        if(!tokenOrgId.equals(organizacaoId)) {
            throw new UnauthorizedAccessException("Você não tem permissão para esta ação");
        }
        return animaisComFormByOrgUseCase.execute(organizacaoId);
    }


    @PostMapping
    public ResponseEntity<String> createFormulario(@RequestBody @Valid FormularioRequestDTO formularioRequestDTO,
                                                 HttpServletRequest request) {

        if (!rateLimiterService.isAllowed(request)) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Limite de envios excedido. Tente novamente mais tarde.");
        }
        createFormularioUseCase.execute(formularioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/aceitar/{id}")
    public ResponseEntity<String> aceitar(@PathVariable Long id) {
        Long tokenOrgId = tokenService.getOrganizacaoId();
        aceitarFormularioByIdUseCase.execute(id, tokenOrgId);
        return ResponseEntity.ok("Email enviado ao adotante");
    }

    @PostMapping("/recusar/{id}")
    public ResponseEntity<String> recusar(@PathVariable Long id, @RequestBody MensagemRecusaDTO mensagemRecusaDTO) {
        Long tokenOrgId = tokenService.getOrganizacaoId();
        recusarFormularioUseCase.execute(mensagemRecusaDTO, id, tokenOrgId);
        return ResponseEntity.ok("Recusado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFormById(@PathVariable Long id){
        Long tokenOrgId = tokenService.getOrganizacaoId();
        deleteFormularioByIdUseCase.execute(id, tokenOrgId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
