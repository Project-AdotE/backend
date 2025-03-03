package com.adote.api.infra.config;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.animal.delete.DeleteAnimalByIdCaseImpl;
import com.adote.api.core.usecases.animal.get.*;
import com.adote.api.core.usecases.animal.patch.UpdateAnimalCase;
import com.adote.api.core.usecases.animal.patch.UpdateAnimalCaseImpl;
import com.adote.api.core.usecases.animal.post.CreateAnimalCase;
import com.adote.api.core.usecases.animal.post.CreateAnimalCaseImpl;
import com.adote.api.infra.gateway.AnimalRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AnimalUseCasesConfig{

    private final AnimalRepositoryGateway animalRepositoryGateway;

    @Bean
    public CreateAnimalCase createAnimalCase(){
        return new CreateAnimalCaseImpl(animalRepositoryGateway);
    }

    @Bean
    public GetAnimaisByOrganizationId getAnimaisByOrganizationId(){
        return new GetAnimaisByOrganizationIdImpl(animalRepositoryGateway);
    }

    @Bean
    public GetAllAnimaisCase getAllAnimaisCase(){
        return new GetAllAnimaisCaseImpl(animalRepositoryGateway);
    }


    @Bean
    public GetAnimalByIdCase getAnimalByIdCase(){
        return new GetAnimalByIdCaseImpl(animalRepositoryGateway);
    }

    @Bean
    public DeleteAnimalByIdCaseImpl deleteAnimalByIdCase(){
        return new DeleteAnimalByIdCaseImpl(animalRepositoryGateway);
    }

    @Bean
    public UpdateAnimalCaseImpl updateAnimalCase(){
        return new UpdateAnimalCaseImpl(animalRepositoryGateway);
    }

}
