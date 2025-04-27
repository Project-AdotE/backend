package com.adote.api.infra.gateway;

import com.adote.api.core.Enums.StatusFormularioEnum;
import com.adote.api.core.entities.Animal;
import com.adote.api.core.entities.Formulario;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.auth.UnauthorizedAccessException;
import com.adote.api.core.exceptions.formulario.FormularioAlreadyExistsException;
import com.adote.api.core.exceptions.formulario.FormularioNotFoundException;
import com.adote.api.core.gateway.FormularioGateway;
import com.adote.api.core.usecases.animal.get.GetAnimalByIdCase;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoById;
import com.adote.api.infra.dtos.animal.response.AnimalSimplesResponseDTO;
import com.adote.api.infra.dtos.formulario.request.FormularioRequestDTO;
import com.adote.api.infra.dtos.formulario.request.MensagemRecusaDTO;
import com.adote.api.infra.dtos.formulario.response.AnimalComFormResponseDTO;
import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;
import com.adote.api.infra.dtos.formulario.response.RespostaResponseDTO;
import com.adote.api.infra.dtos.fotoAnimal.response.FotoAnimalResponseDTO;
import com.adote.api.infra.mappers.AnimalMapper;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.FormularioEntity;
import com.adote.api.infra.persistence.entities.PerguntaEntity;
import com.adote.api.infra.persistence.entities.RespostasEntity;
import com.adote.api.infra.persistence.repositories.FormularioRepository;
import com.adote.api.infra.persistence.repositories.RespostasRepository;
import com.adote.api.infra.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormularioRepositoryGateway implements FormularioGateway {

    private final GetOrganizacaoById getOrganizacaoById;
    private final GetAnimalByIdCase getAnimalByIdCase;

    private final FormularioRepository formularioRepository;
    private final RespostasRepository respostasRepository;

    private final AnimalMapper animalMapper;
    private final OrganizacaoMapper organizacaoMapper;

    private final EmailService emailService;

    @Override
    public void criarFormulario(FormularioRequestDTO formularioRequestDTO) {

        Animal animal = getAnimalByIdCase.execute(formularioRequestDTO.idAnimal());
        Organizacao organizacao = getOrganizacaoById.execute(formularioRequestDTO.idOrganizacao());

        if(!formularioRepository.findByEmailAndAnimal_Id(formularioRequestDTO.email(), formularioRequestDTO.idAnimal()).isEmpty()
            || !formularioRepository.findByCpfAndAnimal_Id(formularioRequestDTO.cpf(), formularioRequestDTO.idAnimal()).isEmpty()){
            throw new FormularioAlreadyExistsException("Email ou CPF já enviou formulario para este animal");
        }

        FormularioEntity formularioEntity = FormularioEntity.builder()
                .animal(animalMapper.toEntity(animal))
                .organizacao(organizacaoMapper.toEntity(organizacao))
                .nomeAdotante(formularioRequestDTO.nomeAdotante())
                .email(formularioRequestDTO.email())
                .idade(formularioRequestDTO.idade())
                .telefone(formularioRequestDTO.telefone())
                .cpf(formularioRequestDTO.cpf())
                .status(StatusFormularioEnum.PENDENTE)
                .dataEnvio(LocalDateTime.now())
                .build();

        FormularioEntity saved = formularioRepository.save(formularioEntity);

        if (formularioRequestDTO.respostas() != null
                && !formularioRequestDTO.respostas().isEmpty()) {
            List<RespostasEntity> respostasEntityList = formularioRequestDTO.respostas().stream()
                    .map(respostaRequestDTO -> RespostasEntity.builder()
                            .formulario(saved)
                            .pergunta(PerguntaEntity.builder().id(respostaRequestDTO.idPergunta() + 2).build())
                            .resposta(respostaRequestDTO.resposta())
                            .build()
                    ).toList();

            respostasRepository.saveAll(respostasEntityList);
        }
    }


    @Override
    public List<FormularioResponseDTO> getFormulariosByAnimalId(Long animalId, Long tokenOrgId) {
        Animal animal = getAnimalByIdCase.execute(animalId);
        if (!tokenOrgId.equals(animal.organizacao().id())){
            throw new UnauthorizedAccessException("Você não tem permissão para acessar isso");
        }

        List<FormularioEntity> formularios = formularioRepository.findAllByAnimal_Id(animalId);

        return formularios.stream().map(formulario -> {
            List<RespostasEntity> respostas = respostasRepository.findAllByFormulario_Id(formulario.getId());

            List<RespostaResponseDTO> respostasDTO = respostas.stream()
                    .map(resposta -> new RespostaResponseDTO(
                            resposta.getId(),
                            resposta.getPergunta().getPergunta(),
                            resposta.getResposta()
                    ))
                    .toList();

            return new FormularioResponseDTO(
                    formulario.getId(),
                    formulario.getNomeAdotante(),
                    formulario.getEmail(),
                    formulario.getIdade(),
                    formulario.getTelefone(),
                    formulario.getCpf(),
                    formulario.getDataEnvio(),
                    respostasDTO
            );
        }).toList();
    }

    @Override
    public List<AnimalComFormResponseDTO> getAnimalComFormByOrganizacaoId(Long organizacaoId) {
        getOrganizacaoById.execute(organizacaoId);

        List<FormularioEntity> formularios = formularioRepository.findAllByOrganizacao_Id(organizacaoId);

        Map<Long, List<FormularioEntity>> formulariosPorAnimal = formularios.stream()
                .collect(Collectors.groupingBy(formulario -> formulario.getAnimal().getId()));

        return formulariosPorAnimal.entrySet().stream()
                .map(entry -> {
                    FormularioEntity formulario = entry.getValue().get(0);
                    var animal = formulario.getAnimal();

                    var primeiraFoto = animal.getFotos() != null && !animal.getFotos().isEmpty()
                            ? animal.getFotos().get(0)
                            : null;

                    return new AnimalComFormResponseDTO(
                            animal.getId(),
                            new AnimalSimplesResponseDTO(
                                    animal.getId(),
                                    animal.getNome(),
                                    primeiraFoto != null ? new FotoAnimalResponseDTO(
                                            primeiraFoto.getId(),
                                            primeiraFoto.getUrl()
                                    ) : null
                            ),
                            entry.getValue().size()
                    );
                })
                .toList();
    }

    @Override
    public void aceitarFormularioById(Long id, Long tokenOrgId) {
        FormularioEntity formulario = formularioRepository.findById(id)
                .orElseThrow(() -> new FormularioNotFoundException("Formulário não existe"));

        if (!tokenOrgId.equals(formulario.getOrganizacao().getId())){
            throw new UnauthorizedAccessException("Você não tem permissão para acessar isso");
        }else if(formulario.getStatus().equals(StatusFormularioEnum.APROVADO)
                || formulario.getStatus().equals(StatusFormularioEnum.RECUSADO)){
            throw new UnauthorizedAccessException("O formulário já foi processado anteriormente");
        }

        formulario.setStatus(StatusFormularioEnum.APROVADO);
        formulario.setDataResposta(LocalDateTime.now());
        formularioRepository.save(formulario);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nomeAdotante", formulario.getNomeAdotante());
        templateModel.put("nomeAnimal", formulario.getAnimal().getNome());
        templateModel.put("tipoAnimal", formulario.getAnimal().getTipo().toString());
        templateModel.put("nomeOrganizacao", formulario.getOrganizacao().getNome());
        templateModel.put("emailOrganizacao", formulario.getOrganizacao().getEmail());
        templateModel.put("telefoneOrganizacao", formulario.getOrganizacao().getNumero());

        emailService.sendHtmlEmail(
                formulario.getEmail(),
                "Parabéns! Seu formulário de adoção foi aprovado",
                "formularioAceitoEmail",
                templateModel
        );
    }

    @Override
    public void recusarFormularioById(MensagemRecusaDTO mensagemRecusaDTO, Long id, Long tokenOrgId) {
        FormularioEntity formulario = formularioRepository.findById(id)
                .orElseThrow(() -> new FormularioNotFoundException("Formulário não existe"));

        if (!tokenOrgId.equals(formulario.getOrganizacao().getId())){
            throw new UnauthorizedAccessException("Você não tem permissão para acessar isso");
        } else if(formulario.getStatus().equals(StatusFormularioEnum.APROVADO)
                || formulario.getStatus().equals(StatusFormularioEnum.RECUSADO)){
            throw new UnauthorizedAccessException("O formulário já foi processado anteriormente");
        }

        formulario.setStatus(StatusFormularioEnum.RECUSADO);
        formulario.setDataResposta(LocalDateTime.now());
        formularioRepository.save(formulario);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nomeAdotante", formulario.getNomeAdotante());
        templateModel.put("nomeAnimal", formulario.getAnimal().getNome());
        templateModel.put("tipoAnimal", formulario.getAnimal().getTipo().toString());
        templateModel.put("nomeOrganizacao", formulario.getOrganizacao().getNome());
        templateModel.put("emailOrganizacao", formulario.getOrganizacao().getEmail());
        templateModel.put("telefoneOrganizacao", formulario.getOrganizacao().getNumero());
        templateModel.put("mensagemRecusa", mensagemRecusaDTO.mensagem());

        emailService.sendHtmlEmail(
                formulario.getEmail(),
                "Infelizmente seu formulário de adoção foi recusado",
                "formularioRecusadoEmail",
                templateModel
        );
    }
}
