package com.adote.api.infra.mappers;

import com.adote.api.core.entities.EnderecoOrganizacao;
import com.adote.api.infra.dtos.enderecoOrganizacao.request.EnderecoRequestDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;
import com.adote.api.infra.persistence.entities.EnderecoOrganizacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "organizacao", ignore = true) // Ignore a referência circular
    EnderecoOrganizacaoEntity toEntity(EnderecoOrganizacao endereco);

    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoOrganizacaoEntity enderecoEntity);
    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoRequestDTO requestDTO);
    EnderecoResponseDTO toResponseDTO(EnderecoOrganizacao endereco);
}
