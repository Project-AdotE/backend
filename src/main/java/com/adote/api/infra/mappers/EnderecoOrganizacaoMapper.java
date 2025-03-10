package com.adote.api.infra.mappers;

import com.adote.api.core.entities.EnderecoOrganizacao;
import com.adote.api.infra.dtos.enderecoOrganizacao.request.EnderecoOrganizacaoRequestDTO;
import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoOrganizacaoResponseDTO;
import com.adote.api.infra.persistence.entities.EnderecoOrganizacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoOrganizacaoMapper {

    EnderecoOrganizacaoEntity toEntity(EnderecoOrganizacao endereco);
    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoOrganizacaoEntity enderecoEntity);
    EnderecoOrganizacao toEnderecoOrganizacao(EnderecoOrganizacaoRequestDTO requestDTO);
    EnderecoOrganizacaoResponseDTO toResponseDTO(EnderecoOrganizacao endereco);

}
