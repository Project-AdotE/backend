package com.adote.api.infra.config.usecases;

import com.adote.api.core.usecases.formulario.get.AnimaisComFormByOrgUseCase;
import com.adote.api.core.usecases.formulario.get.AnimaisComFormByOrgUseCaseImpl;
import com.adote.api.core.usecases.formulario.get.GetAllFormsByAnimalIdUseCase;
import com.adote.api.core.usecases.formulario.get.GetAllFormsByAnimalIdUseCaseImpl;
import com.adote.api.core.usecases.formulario.post.*;
import com.adote.api.infra.gateway.FormularioRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FormularioUseCasesConfig {

    private final FormularioRepositoryGateway formularioRepositoryGateway;

    @Bean
    public CreateFormularioUseCase createFormularioUseCase() {
        return new CreateFormularioUseCaseImpl(formularioRepositoryGateway);
    }

    @Bean
    public GetAllFormsByAnimalIdUseCase getAllFormsByAnimalIdUseCase() {
        return new GetAllFormsByAnimalIdUseCaseImpl(formularioRepositoryGateway);
    }

    @Bean
    public AnimaisComFormByOrgUseCase animaisComFormByOrgUseCase() {
        return new AnimaisComFormByOrgUseCaseImpl(formularioRepositoryGateway);
    }

    @Bean
    public AceitarFormularioByIdUseCase aceitarFormularioByIdUseCase() {
        return new AceitarFormularioByIdUseCaseImpl(formularioRepositoryGateway);
    }

    @Bean
    public RecusarFormularioUseCase recusarFormularioUseCase() {
        return new RecusarFormularioUseCaseImpl(formularioRepositoryGateway);
    }

}
