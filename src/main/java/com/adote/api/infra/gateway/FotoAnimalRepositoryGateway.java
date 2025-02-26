package com.adote.api.infra.gateway;

import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.gateway.FotoAnimalGateway;
import com.adote.api.infra.mappers.FotoAnimalMapper;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import com.adote.api.infra.persistence.repositories.FotoAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FotoAnimalRepositoryGateway implements FotoAnimalGateway {

    private final FotoAnimalRepository fotoAnimalRepository;
    private final FotoAnimalMapper fotoAnimalMapper;

    @Override
    public FotoAnimal createFotoAnimal(FotoAnimal fotoAnimal) {
        FotoAnimalEntity newEntity = fotoAnimalRepository.save(fotoAnimalMapper.toEntity(fotoAnimal));
        return fotoAnimalMapper.toFoto(newEntity);
    }


    public List<FotoAnimal> createMultipleFotos(List<FotoAnimal> fotos) {
        List<FotoAnimalEntity> entities = fotos.stream()
                .map(fotoAnimalMapper::toEntity)
                .toList();

        List<FotoAnimalEntity> savedEntities = fotoAnimalRepository.saveAll(entities);

        return savedEntities.stream()
                .map(fotoAnimalMapper::toFoto)
                .toList();
    }
}
