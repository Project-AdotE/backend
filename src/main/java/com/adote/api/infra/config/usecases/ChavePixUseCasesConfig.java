package com.adote.api.infra.config.usecases;

import com.adote.api.core.usecases.chavePix.patch.UpdateChaveByIdCase;
import com.adote.api.core.usecases.chavePix.patch.UpdateChaveByIdCaseImpl;
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
    public UpdateChaveByIdCase updateChaveByIdCase() {
        return new UpdateChaveByIdCaseImpl(repositoryGateway);
    }

}
