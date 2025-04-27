package com.adote.api.infra.config.usecases;

import com.adote.api.core.usecases.perguntas.GetAllPerguntasUseCase;
import com.adote.api.core.usecases.perguntas.GetAllPerguntasUseCaseImpl;
import com.adote.api.infra.gateway.PerguntaRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PerguntaUseCasesConfig {

    private final PerguntaRepositoryGateway perguntaRepositoryGateway;


    @Bean
    public GetAllPerguntasUseCase getAllPerguntasUseCase() {
        return new GetAllPerguntasUseCaseImpl(perguntaRepositoryGateway);
    }

}
