package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.dtos.organizacao.request.OrganizacaoRequestDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface OrganizacaoMapper {

    OrganizacaoEntity toEntity(Organizacao organizacao);
    Organizacao toOrganizacao(OrganizacaoEntity organizacaoEntity);
    Organizacao toOrganizacao(OrganizacaoRequestDTO requestDTO);
    OrganizacaoResponseDTO toResponseDTO(Organizacao organizacao);

    @AfterMapping
    default void setOrganizacaoInEndereco(@MappingTarget OrganizacaoEntity organizacaoEntity) {
        if (organizacaoEntity != null && organizacaoEntity.getEndereco() != null) {
            organizacaoEntity.getEndereco().setOrganizacao(organizacaoEntity);
        }
    }
}
