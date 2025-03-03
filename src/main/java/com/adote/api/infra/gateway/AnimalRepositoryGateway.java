package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.fotoAnimal.post.CreateMultipleFotosCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.config.s3.S3StorageService;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import com.adote.api.infra.mappers.FotoAnimalMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.repositories.AnimalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnimalRepositoryGateway implements AnimalGateway {

    private final AnimalRepository animalRepository;
    private final GetOrganizacaoById getOrganizacaoById;
    private final CreateMultipleFotosCase createMultipleFotosCase;
    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;
    private final S3StorageService s3StorageService;


    @Override
    public Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());

        if (organizacaoOptional.isPresent()) {
            AnimalEntity animalEntity = animalMapper.toEntity(animalRequestDTO);
            animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacaoOptional.get()));

            AnimalEntity savedAnimal = animalRepository.save(animalEntity);

            if(fotos != null) {
                List<FotoAnimal> fotoAnimalList = fotos.stream().map(foto -> {
                    String url = s3StorageService.uploadFile(foto);
                    return new FotoAnimal(null, url, animalMapper.toAnimal(savedAnimal));
                }).toList();

                createMultipleFotosCase.execute(fotoAnimalList);
            }

            return animalMapper.toAnimal(savedAnimal);
        }
        return null;
    }

    @Override
    public Page<Animal> getAnimaisByOrganizationId(Long id, Pageable pageable) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(id);
        if (organizacaoOptional.isPresent()) {
            Page<AnimalEntity> animalPage = animalRepository.findAllByOrganizacao_IdOrderByCreatedAtDesc(id, pageable);
            return animalPage.map(animalMapper::toAnimal);
        }
        return Page.empty();
    }

    @Override
    public Page<Animal> getAllAnimaisCase(Pageable pageable) {
        Page<AnimalEntity> animalPage = animalRepository.findAllByOrderByCreatedAtDesc(pageable);
        return animalPage.map(animalMapper::toAnimal);
    }

    @Override
    public Optional<Animal> getAnimalById(Long id) {
        Optional<AnimalEntity> animalOpt = animalRepository.findById(id);
        if (animalOpt.isPresent()) {
            AnimalEntity animalEntity = animalOpt.get();
            return Optional.of(animalMapper.toAnimal(animalEntity));
        }
        return Optional.empty();
    }


    @Transactional
    @Override
    public void deleteAnimalById(Long id) {
        this.getAnimalById(id).ifPresent(animal -> {
            animalRepository.deleteById(animal.id());
        });
    }
}
