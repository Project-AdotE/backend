package com.adote.api.infra.config;

import com.adote.api.core.usecases.chavePix.get.GetAllChavesCase;
import com.adote.api.core.usecases.chavePix.get.GetAllChavesCaseImpl;
import com.adote.api.core.usecases.chavePix.post.CreateChaveCase;
import com.adote.api.core.usecases.chavePix.post.CreateChaveCaseImpl;
import com.adote.api.infra.gateway.ChavePixRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChavePixUseCasesConfig {

    private final ChavePixRepositoryGateway repositoryGateway;

    @Bean
    public CreateChaveCase createChaveCase() {
        return new CreateChaveCaseImpl(repositoryGateway);
    }

    @Bean
    public GetAllChavesCase getAllChaveCase() {
        return new GetAllChavesCaseImpl(repositoryGateway);
    }

}
