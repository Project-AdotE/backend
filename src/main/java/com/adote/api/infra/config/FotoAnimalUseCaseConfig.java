package com.adote.api.infra.config;

import com.adote.api.core.usecases.fotoAnimal.post.CreateFotoAnimalCase;
import com.adote.api.core.usecases.fotoAnimal.post.CreateFotoAnimalCaseImpl;
import com.adote.api.infra.gateway.FotoAnimalRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FotoAnimalUseCaseConfig {

    private final FotoAnimalRepositoryGateway fotoAnimalRepositoryGateway;

    @Bean
    public CreateFotoAnimalCase createFotoAnimalCase() {
        return new CreateFotoAnimalCaseImpl(fotoAnimalRepositoryGateway);
    }

}
