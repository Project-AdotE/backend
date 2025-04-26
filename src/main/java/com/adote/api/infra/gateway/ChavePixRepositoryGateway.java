package com.adote.api.infra.gateway;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.auth.UnauthorizedAccessException;
import com.adote.api.core.exceptions.chavePix.ChaveAlreadyTakenException;
import com.adote.api.core.gateway.ChavePixGateway;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.dtos.chavePix.update.ChavePixUpdateDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.ChavePixEntity;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.ChavePixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChavePixRepositoryGateway implements ChavePixGateway {


    private final GetOrganizacaoById getOrganizacaoById;

    private final ChavePixRepository chavePixRepository;

    private final ChavePixMapper chavePixMapper;
    private final OrganizacaoMapper organizacaoMapper;

    @Override
    public ChavePix createChavePix(ChavePixRequestDTO chavePixRequestDTO) {
        Organizacao organizacao = getOrganizacaoById.execute(chavePixRequestDTO.organizacaoId());

        ChavePixEntity chavePixEntity = chavePixMapper.toEntity(chavePixRequestDTO);
        chavePixEntity.setOrganizacao(organizacaoMapper.toEntity(organizacao));
        chavePixRepository.save(chavePixEntity);
        return chavePixMapper.toChavePix(chavePixEntity);
    }

    @Override
    public ChavePix updateChaveById(Long id, Long tokenOrgId, ChavePixUpdateDTO updateDTO) {
        ChavePixEntity entity = chavePixRepository.findById(id);
        if(!Objects.equals(tokenOrgId, entity.getOrganizacao().getId())) {
            throw new UnauthorizedAccessException("Você não tem permissão para atualizar a chave desta organização");
        }

        if(updateDTO.tipo().isPresent()){
            entity.setTipo(updateDTO.tipo().get());
        }
        if(updateDTO.chave().isPresent()){
            if (chavePixRepository.existsByChaveAndIdNot(updateDTO.chave().get(), id)) {
                throw new ChaveAlreadyTakenException("Chave já escolhida");
            }
            entity.setChave(updateDTO.chave().get());
        }

        ChavePixEntity savedEntity = chavePixRepository.save(entity);
        return chavePixMapper.toChavePix(savedEntity);
    }
}
