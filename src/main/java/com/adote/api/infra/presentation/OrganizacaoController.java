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
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
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
    private final GetAllAnimaisCase getAllAnimaisCase;
    private final GetChavesByOrgIdCase getChavesByOrgIdCase;

    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;

    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAllOrganizacoes(
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 20);

        Page<Organizacao> organizacaoPage;
        if (cidade != null || estado != null) {
            organizacaoPage = getAllOrganizacoesCase.execute(cidade, estado, pageable);
        } else {
            organizacaoPage = getAllOrganizacoesCase.execute(pageable);
        }

        List<OrganizacaoResponseDTO> organizacaoResponseDTOList = organizacaoPage.stream()
                .map(organizacaoMapper::toResponseDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("organizacoes", organizacaoResponseDTOList);
        response.put("currentPage", organizacaoPage.getNumber());
        response.put("totalItems", organizacaoPage.getTotalElements());
        response.put("totalPages", organizacaoPage.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/find")
    public ResponseEntity<OrganizacaoResponseDTO> findOrganizacaoById(
            @RequestParam Long id,
            @RequestParam(defaultValue = "true") boolean includeAnimals,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) TipoAnimalEnum tipo,
            @RequestParam(required = false) IdadeEnum idade,
            @RequestParam(required = false) PorteEnum porte,
            @RequestParam(required = false) SexoEnum sexo) {

        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(id);

        if (organizacaoOptional.isPresent()) {
            Organizacao organizacao = organizacaoOptional.get();
            OrganizacaoResponseDTO responseDTO = organizacaoMapper.toResponseDTO(organizacao);

            Map<String, Object> animaisData = null;
            if (includeAnimals) {
                Pageable pageable = PageRequest.of(page, 20);

                Page<Animal> animalPage = getAllAnimaisCase.execute(
                        tipo, idade, porte, sexo, id, pageable);

                List<AnimalResponseDTO> animalResponseDTOList = animalPage.stream()
                        .map(animalMapper::toResponseDTO)
                        .collect(Collectors.toList());

                animaisData = new HashMap<>();
                animaisData.put("content", animalResponseDTOList);
                animaisData.put("currentPage", animalPage.getNumber());
                animaisData.put("totalItems", animalPage.getTotalElements());
                animaisData.put("totalPages", animalPage.getTotalPages());
            }

            List<ChavePixSimplificadaDTO> chavesPix = getChavesPix(id);

            responseDTO = new OrganizacaoResponseDTO(
                    responseDTO.id(),
                    responseDTO.nome(),
                    responseDTO.numero(),
                    responseDTO.cnpj(),
                    responseDTO.endereco(),
                    responseDTO.email(),
                    animaisData,
                    chavesPix
            );

            return ResponseEntity.ok(responseDTO);
        }

        return ResponseEntity.notFound().build();
    }

    private List<ChavePixSimplificadaDTO> getChavesPix(Long organizacaoId) {
        List<ChavePix> chavesPix = getChavesByOrgIdCase.execute(organizacaoId);

        return chavesPix.stream()
                .map(chavePix -> new ChavePixSimplificadaDTO(
                        chavePix.id(),
                        chavePix.tipo(),
                        chavePix.chave()))
                .collect(Collectors.toList());
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
