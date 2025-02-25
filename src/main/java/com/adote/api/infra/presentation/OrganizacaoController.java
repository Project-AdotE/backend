package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.usecases.organizacao.delete.DeleteOrganizacaoById;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/organizacao")
@RequiredArgsConstructor
public class OrganizacaoController {

    private final GetOrganizacaoById getOrganizacaoById;
    private final DeleteOrganizacaoById deleteOrganizacaoById;
    private final OrganizacaoMapper organizacaoMapper;


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
