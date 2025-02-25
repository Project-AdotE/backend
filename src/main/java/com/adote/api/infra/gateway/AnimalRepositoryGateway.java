package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnimalRepositoryGateway implements AnimalGateway {

    private final AnimalRepository animalRepository;
    private final GetOrganizacaoById getOrganizacaoById;
    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;

    @Override
    public Animal createAnimal(AnimalRequestDTO animalRequestDTO) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());
        if (organizacaoOptional.isPresent()) {
            AnimalEntity animalEntity = animalMapper.toEntity(animalRequestDTO);
            animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacaoOptional.get()));
            return animalMapper.toAnimal(animalRepository.save(animalEntity));
        }
        return null;
    }

    @Override
    public List<Animal> getAnimaisByOrganizationId(Long id) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(id);
        if (organizacaoOptional.isPresent()) {
            List<AnimalEntity> animalList = animalRepository.findAllByOrganizacao_Id(id);
            return animalList.stream()
                    .map(animalMapper::toAnimal)
                    .toList();
        }
        return null;
    }

    @Override
    public List<Animal> getAllAnimaisCase() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toAnimal)
                .toList();
    }
}
