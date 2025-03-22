package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.fotoAnimal.get.GetFotoByUrlCase;
import com.adote.api.core.usecases.fotoAnimal.post.CreateMultipleFotosCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.config.aws.s3.S3StorageService;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
import com.adote.api.infra.filters.animal.AnimalFilter;
import com.adote.api.infra.filters.animal.AnimalSpecification;
import com.adote.api.infra.mappers.AnimalMapper;
import com.adote.api.infra.mappers.FotoAnimalMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.AnimalEntity;
import com.adote.api.infra.persistence.entities.FotoAnimalEntity;
import com.adote.api.infra.persistence.repositories.AnimalRepository;
import com.adote.api.infra.persistence.repositories.FotoAnimalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnimalRepositoryGateway implements AnimalGateway {

    private final FotoAnimalRepository fotoAnimalRepository;
    private final AnimalRepository animalRepository;

    private final GetOrganizacaoById getOrganizacaoById;

    private final GetFotoByUrlCase getFotoByUrlCase;
    private final CreateMultipleFotosCase createMultipleFotosCase;

    private final S3StorageService s3StorageService;

    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;
    private final FotoAnimalMapper fotoAnimalMapper;


    @Override
    public Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos) {
        Organizacao organizacao = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());

        AnimalEntity animalEntity = animalMapper.toEntity(animalRequestDTO);
        animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacao));

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

    @Override
    public Animal updateAnimal(Long id, AnimalRequestDTO animalRequestDTO,
                               List<MultipartFile> novasFotos, List<String> fotosParaRemover) {
        Optional<AnimalEntity> animalOptional = animalRepository.findById(id);

        if (animalOptional.isEmpty()) {
            return null;
        }

        AnimalEntity animalEntity = animalOptional.get();

        if (animalRequestDTO != null) {
            if (animalRequestDTO.nome() != null) animalEntity.setNome(animalRequestDTO.nome());
            if (animalRequestDTO.descricao() != null) animalEntity.setDescricao(animalRequestDTO.descricao());

            if (animalRequestDTO.tipo() != null) animalEntity.setTipo(animalRequestDTO.tipo());
            if (animalRequestDTO.sexo() != null) animalEntity.setSexo(animalRequestDTO.sexo());
            if (animalRequestDTO.porte() != null) animalEntity.setPorte(animalRequestDTO.porte());
            if (animalRequestDTO.idade() != null) animalEntity.setIdade(animalRequestDTO.idade());

            if (animalRequestDTO.vacinado() != null) animalEntity.setVacinado(animalRequestDTO.vacinado());
            if (animalRequestDTO.castrado() != null) animalEntity.setCastrado(animalRequestDTO.castrado());
            if (animalRequestDTO.vermifugado() != null) animalEntity.setVermifugado(animalRequestDTO.vermifugado());
            if (animalRequestDTO.srd() != null) animalEntity.setSrd(animalRequestDTO.srd());

            if (animalRequestDTO.organizacao_id() != null) {
                Organizacao organizacao = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());
                animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacao));
            }

            animalRepository.save(animalEntity);
        }

        if (fotosParaRemover != null && !fotosParaRemover.isEmpty()) {
            for (String fotoUrl : fotosParaRemover) {
                Optional<FotoAnimal> fotoAnimalOptional = getFotoByUrlCase.getFotoAnimalByUrl(fotoUrl);
                if (fotoAnimalOptional.isPresent()) {
                    FotoAnimal fotoAnimal = fotoAnimalOptional.get();

                    s3StorageService.deleteFile(fotoUrl);

                    fotoAnimalRepository.delete(fotoAnimalMapper.toEntity(fotoAnimal));
                }
            }
        }

        if (novasFotos != null && !novasFotos.isEmpty()) {
            List<FotoAnimal> fotoAnimalList = novasFotos.stream().map(foto -> {
                String url = s3StorageService.uploadFile(foto);
                return new FotoAnimal(null, url, animalMapper.toAnimal(animalEntity));
            }).toList();

            createMultipleFotosCase.execute(fotoAnimalList);
        }

        return animalMapper.toAnimal(animalEntity);
    }

    @Override
    public Page<Animal> getAllAnimais(AnimalFilter filter, Pageable pageable) {
        if(filter.getOrganizacaoId() != null) {
            getOrganizacaoById.execute(filter.getOrganizacaoId());
        }

        AnimalSpecification spec = new AnimalSpecification(filter);
        Page<AnimalEntity> animalEntities = animalRepository.findAll(spec, pageable);
        return animalEntities.map(animalMapper::toAnimal);
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
        Optional<AnimalEntity> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            AnimalEntity animalEntity = animalOptional.get();

            List<FotoAnimalEntity> fotosDoAnimal = fotoAnimalRepository.getFotoAnimalEntitiesByAnimal_Id(id);

            for (FotoAnimalEntity foto : fotosDoAnimal) {
                s3StorageService.deleteFile(foto.getUrl());

                fotoAnimalRepository.delete(foto);
            }

            animalRepository.delete(animalEntity);
        }
    }
}
