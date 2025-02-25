package com.adote.api.infra.mappers;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.dtos.organizacao.request.OrganizacaoRequestDTO;
import com.adote.api.infra.dtos.organizacao.response.OrganizacaoResponseDTO;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizacaoMapper {

    OrganizacaoEntity toEntity(Organizacao organizacao);

    Organizacao toOrganizacao(OrganizacaoEntity organizacaoEntity);
    Organizacao toOrganizacao(OrganizacaoRequestDTO requestDTO);

    OrganizacaoResponseDTO toResponseDTO(Organizacao organizacao);

}
