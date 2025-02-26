package com.adote.api.infra.dtos.animal.request;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AnimalWithPhotosRequestDTO {

    private String nome;
    private SexoEnum sexo;
    private PorteEnum porte;
    private Boolean vacinado;
    private Long organizacaoId;
    private List<MultipartFile> fotos;

}
