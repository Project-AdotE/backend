package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.FotoAnimal;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.animal.AnimalNotFoundException;
import com.adote.api.core.exceptions.auth.UnauthorizedAccessException;
import com.adote.api.core.gateway.AnimalGateway;
import com.adote.api.core.usecases.formulario.delete.DeleteFormularioByIdUseCase;
import com.adote.api.core.usecases.formulario.get.GetAllFormsByAnimalIdUseCase;
import com.adote.api.core.usecases.fotoAnimal.get.GetFotoByUrlCase;
import com.adote.api.core.usecases.fotoAnimal.post.CreateMultipleFotosCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.animal.request.AnimalPatchDTO;
import com.adote.api.infra.persistence.entities.FormularioEntity;
import com.adote.api.infra.persistence.repositories.FormularioRepository;
import com.adote.api.infra.service.ImageOptimizationService;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnimalRepositoryGateway implements AnimalGateway {

    private final FotoAnimalRepository fotoAnimalRepository;
    private final AnimalRepository animalRepository;
    private final FormularioRepository formularioRepository;

    private final GetOrganizacaoById getOrganizacaoById;

    private final GetFotoByUrlCase getFotoByUrlCase;
    private final CreateMultipleFotosCase createMultipleFotosCase;

    private final S3StorageService s3StorageService;
    private final ImageOptimizationService imageOptimizationService;

    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;
    private final FotoAnimalMapper fotoAnimalMapper;


    @Override
    public Animal createAnimal(AnimalRequestDTO animalRequestDTO, List<MultipartFile> fotos) {
        Organizacao organizacao = getOrganizacaoById.execute(animalRequestDTO.organizacao_id());

        AnimalEntity animalEntity = animalMapper.toEntity(animalRequestDTO);
        animalEntity.setOrganizacao(organizacaoMapper.toEntity(organizacao));
        animalEntity.setAdotado(Boolean.FALSE);

        AnimalEntity savedAnimal = animalRepository.save(animalEntity);

        if(fotos != null && (!fotos.isEmpty() && fotos.size() < 4)) {
            List<FotoAnimal> fotoAnimalList = fotos.stream().map(foto -> {

                try{
                    byte[] optimizedImage = imageOptimizationService.optimizeImage(foto);
                    MultipartFile optimizedFile = imageOptimizationService.convertToMultipartFile(optimizedImage, foto.getOriginalFilename());

                    String url = s3StorageService.uploadFile(optimizedFile, "animais");
                    return new FotoAnimal(null, url, animalMapper.toAnimal(savedAnimal));
                }catch (IOException e) {
                    throw new RuntimeException("Erro ao otimizar imagem", e);
                }
            }).toList();

            createMultipleFotosCase.execute(fotoAnimalList);
        }

        return animalMapper.toAnimal(savedAnimal);
    }

    @Override
    public Animal updateAnimal(Long id, AnimalPatchDTO animalRequestDTO,
                               List<MultipartFile> novasFotos, List<String> fotosParaRemover, Long tokenOrgId) {

        AnimalEntity animalEntity = animalRepository.findByIdAndAdotadoFalse(id)
                .orElseThrow(() -> new AnimalNotFoundException(id.toString()));

        if (!tokenOrgId.equals(animalEntity.getOrganizacao().getId())) {
            throw new UnauthorizedAccessException("Vocês não tem permissão para atualizar o animal");
        }

        animalRequestDTO.nome().ifPresent(animalEntity::setNome);
        animalRequestDTO.descricao().ifPresent(animalEntity::setDescricao);
        animalRequestDTO.tipo().ifPresent(animalEntity::setTipo);
        animalRequestDTO.sexo().ifPresent(animalEntity::setSexo);
        animalRequestDTO.porte().ifPresent(animalEntity::setPorte);
        animalRequestDTO.idade().ifPresent(animalEntity::setIdade);
        animalRequestDTO.vacinado().ifPresent(animalEntity::setVacinado);
        animalRequestDTO.castrado().ifPresent(animalEntity::setCastrado);
        animalRequestDTO.vermifugado().ifPresent(animalEntity::setVermifugado);
        animalRequestDTO.srd().ifPresent(animalEntity::setSrd);
        animalRequestDTO.adotado().ifPresent(animalEntity::setAdotado);

        if(animalRequestDTO.adotado().isPresent() && animalRequestDTO.adotado().get()) {
            List<FormularioEntity> formulariosAnimal = formularioRepository.findAllByAnimal_Id(animalEntity.getId());
            if(!formulariosAnimal.isEmpty()) {
                formulariosAnimal.forEach(formularioEntity -> {
                    formularioRepository.deleteById(formularioEntity.getId());
                });
            }
        }

        animalRepository.save(animalEntity);

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

        if (novasFotos != null && !novasFotos.isEmpty() && novasFotos.size() < 4) {
            List<FotoAnimal> fotoAnimalList = novasFotos.stream().map(foto -> {
                String url = s3StorageService.uploadFile(foto, "animais");
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
        Optional<AnimalEntity> animalOpt = animalRepository.findByIdAndAdotadoFalse(id);
        return animalOpt.map(animalMapper::toAnimal);
    }

    @Transactional
    @Override
    public void deleteAnimalById(Long id, Long tokenOrgId) {
        Optional<AnimalEntity> animalOptional = animalRepository.findByIdAndAdotadoFalse(id);
        if(animalOptional.isEmpty()){
            throw new AnimalNotFoundException(id.toString());
        }

        AnimalEntity animalEntity = animalOptional.get();

        if(!tokenOrgId.equals(animalEntity.getOrganizacao().getId())) {
            throw new UnauthorizedAccessException("Você não pode deletar esse animal");
        }

        List<FotoAnimalEntity> fotosDoAnimal = fotoAnimalRepository.getFotoAnimalEntitiesByAnimal_Id(id);

        for (FotoAnimalEntity foto : fotosDoAnimal) {
            s3StorageService.deleteFile(foto.getUrl());

            fotoAnimalRepository.delete(foto);
        }

        animalRepository.delete(animalEntity);
    }
}
