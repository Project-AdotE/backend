package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoById;
import com.adote.api.core.usecases.organizacao.get.GetAllOrganizacoesCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/organizacao")
@RequiredArgsConstructor
@Tag(name = "Organização", description = "Responsavel pelo gerenciamento de organizações")
public class OrganizacaoController {

    private final GetOrganizacaoById getOrganizacaoById;
    private final GetAllOrganizacoesCase getAllOrganizacoesCase;
    private final DeleteOrganizacaoById deleteOrganizacaoById;
    private final OrganizacaoMapper organizacaoMapper;

    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAllOrganizacoes(
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 20);

        Page<Organizacao> organizacaoPage = getAllOrganizacoesCase.execute(pageable);

        List<OrganizacaoResponseDTO> organizacaoResponseDTOList = organizacaoPage.stream()
                .map(organizacaoMapper::toResponseDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("animals", organizacaoResponseDTOList);
        response.put("currentPage", organizacaoPage.getNumber());
        response.put("totalItems", organizacaoPage.getTotalElements());
        response.put("totalPages", organizacaoPage.getTotalPages());

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/find")
    public ResponseEntity<OrganizacaoResponseDTO> findOrganizacaoById(@RequestParam Long id) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(id);
        if (organizacaoOptional.isPresent()) {
            return ResponseEntity.ok(organizacaoMapper.toResponseDTO(organizacaoOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOrganizacaoById(@RequestParam Long id) {
        Optional<Organizacao> organizacaoEntity = getOrganizacaoById.execute(id);
        if (organizacaoEntity.isPresent()) {
            deleteOrganizacaoById.execute(id);
        }
        return ResponseEntity.noContent().build();
    }

}
