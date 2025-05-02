package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoById;
import com.adote.api.core.usecases.organizacao.get.GetAllOrganizacoesCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoBaseDTO;
import com.adote.api.infra.dtos.page.response.PageResponseDTO;
import com.adote.api.infra.filters.organizacao.OrganizacaoFilter;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.repositories.AnimalRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organizacao")
@RequiredArgsConstructor
@Tag(name = "Organização", description = "Responsavel pelo gerenciamento de organizações")
public class OrganizacaoController {

    private final GetOrganizacaoById getOrganizacaoById;
    private final GetAllOrganizacoesCase getAllOrganizacoesCase;
    private final DeleteOrganizacaoById deleteOrganizacaoById;

    private final AnimalRepository animalRepository;

    private final TokenService tokenService;

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
        Organizacao organizacao = getOrganizacaoById.execute(id);
        OrganizacaoBaseDTO dto = organizacaoMapper.toBaseDTO(organizacao);

        Long totalAdotados = animalRepository.countAdotadosByOrganizacao(id);
        dto = new OrganizacaoBaseDTO(
                dto.id(), dto.nome(), dto.numero(), dto.cnpj(),
                dto.endereco(), dto.chavesPix(), dto.email(),
                totalAdotados
        );

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizacao(@PathVariable Long id) {
        Long tokenOrgId = tokenService.getOrganizacaoId();

        if (!tokenOrgId.equals(id)) {
            throw new RuntimeException("Você não pode excluir a organização.");
        }

        deleteOrganizacaoById.execute(id);
        return ResponseEntity.noContent().build();
    }

}
