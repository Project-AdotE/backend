package com.adote.api.core.gateway;

import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;
import com.adote.api.infra.dtos.formulario.request.MensagemRecusaDTO;
import com.adote.api.infra.dtos.formulario.response.AnimalComFormResponseDTO;
import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;

import java.util.List;

public interface FormularioGateway {

    void criarFormulario(FormularioRequestDTO formularioRequestDTO);

    void aceitarFormularioById(Long id, Long tokenOrgId);

    void recusarFormularioById(MensagemRecusaDTO mensagemRecusaDTO, Long id, Long tokenOrgId);

    List<FormularioResponseDTO> getFormulariosByAnimalId(Long animalId, Long tokenOrgId);

    List<AnimalComFormResponseDTO> getAnimalComFormByOrganizacaoId(Long organizacaoId);
}
