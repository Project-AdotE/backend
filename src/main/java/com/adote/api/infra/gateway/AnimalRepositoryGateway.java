package com.adote.api.infra.gateway;

import com.adote.api.core.Enums.IdadeEnum;
import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import com.adote.api.core.Enums.TipoAnimalEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.fotoAnimal.get.GetFotoByUrlCase;
import com.adote.api.core.usecases.fotoAnimal.post.CreateMultipleFotosCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.config.s3.S3StorageService;
import com.adote.api.infra.dtos.animal.request.AnimalRequestDTO;
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
    private final FotoAnimalRepository fotoAnimalRepository;
    private final GetFotoByUrlCase getFotoByUrlCase;
    private final GetOrganizacaoById getOrganizacaoById;
    private final CreateMultipleFotosCase createMultipleFotosCase;
    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;
    private final S3StorageService s3StorageService;
    private final FotoAnimalMapper fotoAnimalMapper;


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
    public Animal updateAnimal(Long id, AnimalRequestDTO animalRequestDTO,
                               List<MultipartFile> novasFotos, List<String> fotosParaRemover) {
        Optional<AnimalEntity> animalOptional = animalRepository.findById(id);

        if (animalOptional.isEmpty()) {
            return null;
        }

        AnimalEntity animalEntity = animalOptional.get();

        // Atualizar dados do animal se necessário
        if (animalRequestDTO != null) {
            // Campos de texto
            if (animalRequestDTO.nome() != null) animalEntity.setNome(animalRequestDTO.nome());
            if (animalRequestDTO.descricao() != null) animalEntity.setDescricao(animalRequestDTO.descricao());

            // Campos Enum
            if (animalRequestDTO.tipo() != null) animalEntity.setTipo(animalRequestDTO.tipo());
            if (animalRequestDTO.sexo() != null) animalEntity.setSexo(animalRequestDTO.sexo());
            if (animalRequestDTO.porte() != null) animalEntity.setPorte(animalRequestDTO.porte());
            if (animalRequestDTO.idade() != null) animalEntity.setIdade(animalRequestDTO.idade());

            // Campos booleanos
            if (animalRequestDTO.vacinado() != null) animalEntity.setVacinado(animalRequestDTO.vacinado());
            if (animalRequestDTO.castrado() != null) animalEntity.setCastrado(animalRequestDTO.castrado());
            if (animalRequestDTO.vermifugado() != null) animalEntity.setVermifugado(animalRequestDTO.vermifugado());
            if (animalRequestDTO.srd() != null) animalEntity.setSrd(animalRequestDTO.srd());

            // Se houver alteração na organização
            if (animalRequestDTO.organizacao_id() != null) {
                Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());
                if (organizacaoOptional.isPresent()) {
                    animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacaoOptional.get()));
                }
            }

            animalRepository.save(animalEntity);
        }

        // Remover fotos selecionadas
        if (fotosParaRemover != null && !fotosParaRemover.isEmpty()) {
            for (String fotoUrl : fotosParaRemover) {
                Optional<FotoAnimal> fotoAnimalOptional = getFotoByUrlCase.getFotoAnimalByUrl(fotoUrl);
                if (fotoAnimalOptional.isPresent()) {
                    FotoAnimal fotoAnimal = fotoAnimalOptional.get();

                    // Remover arquivo do S3
                    s3StorageService.deleteFile(fotoUrl);

                    // Remover registro do banco
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
    public Page<Animal> getAnimaisByOrganizationId(Long id, Pageable pageable) {
        Optional<Organizacao> organizacaoOptional = getOrganizacaoById.execute(id);
        if (organizacaoOptional.isPresent()) {
            Page<AnimalEntity> animalPage = animalRepository.findAllByOrganizacao_IdOrderByCreatedAtDesc(id, pageable);
            return animalPage.map(animalMapper::toAnimal);
        }
        return Page.empty();
    }

    @Override
    public Page<Animal> getAllAnimaisCase(
            TipoAnimalEnum tipo,
            IdadeEnum idade,
            PorteEnum porte,
            SexoEnum sexo,
            Long orgId,
            Pageable pageable) {

        Page<AnimalEntity> animalEntities = animalRepository.findAllWithFilters(tipo, idade, porte, sexo, orgId, pageable);
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

            // Buscar todas as fotos associadas ao animal
            List<FotoAnimalEntity> fotosDoAnimal = fotoAnimalRepository.getFotoAnimalEntitiesByAnimal_Id(id);

            // Excluir fotos do S3
            for (FotoAnimalEntity foto : fotosDoAnimal) {
                // Deletar arquivo do S3
                s3StorageService.deleteFile(foto.getUrl());

                fotoAnimalRepository.delete(foto);
            }

            // Deletar o animal
            animalRepository.delete(animalEntity);
        }
    }
}
