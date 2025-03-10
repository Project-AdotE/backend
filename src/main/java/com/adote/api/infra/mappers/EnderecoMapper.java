package com.adote.api.infra.mappers;

import com.adote.api.core.entities.EnderecoOrganizacao;
import com.adote.api.infra.dtos.enderecoOrganizacao.request.EnderecoRequestDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;
import com.adote.api.infra.persistence.entities.EnderecoOrganizacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoOrganizacaoEntity toEntity(EnderecoOrganizacao endereco);
    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoOrganizacaoEntity enderecoEntity);
    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoRequestDTO requestDTO);
    EnderecoResponseDTO toResponseDTO(EnderecoOrganizacao endereco);

}
