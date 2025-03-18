package com.adote.api.infra.presentation;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.usecases.animal.get.GetAllAnimaisCase;
import com.adote.api.core.usecases.chavePix.get.GetChavesByOrgIdCase;
import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoById;
import com.adote.api.core.usecases.organizacao.get.GetAllOrganizacoesCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.animal.response.AnimalResponseDTO;
import com.adote.api.infra.dtos.chavePix.response.ChavePixSimplificadaDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoBaseDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.dtos.page.response.PageResponseDTO;
import com.adote.api.infra.filters.organizacao.OrganizacaoFilter;
import com.adote.api.infra.mappers.AnimalMapper;
import com.adote.api.infra.mappers.ChavePixMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organizacao")
@RequiredArgsConstructor
@Tag(name = "Organização", description = "Responsavel pelo gerenciamento de organizações")
public class OrganizacaoController {

    private final GetOrganizacaoById getOrganizacaoById;
    private final GetAllOrganizacoesCase getAllOrganizacoesCase;
    private final DeleteOrganizacaoById deleteOrganizacaoById;

    private final OrganizacaoMapper organizacaoMapper;

    @GetMapping
    public ResponseEntity<PageResponseDTO<OrganizacaoBaseDTO>> getAllOrganizacoes(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page) {

        OrganizacaoFilter filter = new OrganizacaoFilter();
        filter.setCidade(cidade);
        filter.setEstado(estado);

        Pageable pageable = PageRequest.of(page, 20);

        Page<Organizacao> organizacaoPage = getAllOrganizacoesCase.execute(filter, pageable);

        List<OrganizacaoBaseDTO> organizacaoBaseDTOList = organizacaoPage.getContent().stream()
                .map(organizacaoMapper::toBaseDTO)
                .collect(Collectors.toList());

        PageResponseDTO<OrganizacaoBaseDTO> response = new PageResponseDTO<>(
                organizacaoBaseDTOList,
                organizacaoPage.getNumber(),
                organizacaoPage.getTotalElements(),
                organizacaoPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizacaoBaseDTO> getOrganizacaoById(@PathVariable Long id) {
        return getOrganizacaoById.execute(id)
                .map(organizacao -> ResponseEntity.ok(organizacaoMapper.toBaseDTO(organizacao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizacao(@PathVariable Long id) {
        return getOrganizacaoById.execute(id)
                .map(organizacao -> {
                    deleteOrganizacaoById.execute(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
