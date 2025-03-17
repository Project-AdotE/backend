package com.adote.api.infra.gateway;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.ChavePixGateway;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.chavePix.request.ChavePixRequestDTO;
import com.adote.api.infra.mappers.ChavePixMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.ChavePixEntity;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.ChavePixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChavePixRepositoryGateway implements ChavePixGateway {


    private final GetOrganizacaoById getOrganizacaoById;

    private final ChavePixRepository chavePixRepository;

    private final ChavePixMapper chavePixMapper;
    private final OrganizacaoMapper organizacaoMapper;

    @Override
    public ChavePix createChavePix(ChavePixRequestDTO chavePixRequestDTO) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(chavePixRequestDTO.organizacao_id());
        if (organizacaoOptional.isPresent()) {
            OrganizacaoEntity organizacao = organizacaoMapper.toEntity(organizacaoOptional.get());
            ChavePixEntity chavePixEntity = chavePixMapper.toEntity(chavePixRequestDTO);
            chavePixEntity.setOrganizacao(organizacao);
            chavePixRepository.save(chavePixEntity);
            return chavePixMapper.toChavePix(chavePixEntity);
        }
        return null;
    }

    @Override
    public List<ChavePix> getAllChaves() {
        List<ChavePixEntity> chavePixEntities = chavePixRepository.findAll();
        return chavePixEntities.stream()
                .map(chavePixMapper::toChavePix)
                .toList();
    }

    @Override
    public List<ChavePix> getChavePixByOrgId(Long orgId) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(orgId);
        if (organizacaoOptional.isPresent()) {
            List<ChavePixEntity> chavePixEntityList = chavePixRepository.findAllByOrganizacao_Id(orgId);
            return chavePixEntityList.stream()
                    .map(chavePixMapper::toChavePix)
                    .toList();
        }
        return List.of();
    }
}
