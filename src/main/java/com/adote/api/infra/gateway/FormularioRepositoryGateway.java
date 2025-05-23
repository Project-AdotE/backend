package com.adote.api.infra.gateway;

import com.adote.api.core.Enums.StatusFormularioEnum;
import com.adote.api.core.entities.Animal;
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
import com.adote.api.infra.presentation.NotificationController;
import com.adote.api.infra.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final NotificationController notificationController;

    @Override
    public void criarFormulario(FormularioRequestDTO formularioRequestDTO) {
        Animal animal = getAnimalByIdCase.execute(formularioRequestDTO.idAnimal());
        Organizacao organizacao = getOrganizacaoById.execute(formularioRequestDTO.idOrganizacao());

        if(formularioRepository.existsByEmailAndAnimal_Id(formularioRequestDTO.email(), formularioRequestDTO.idAnimal())
                || formularioRepository.existsByCpfAndAnimal_Id(formularioRequestDTO.cpf(), formularioRequestDTO.idAnimal())){
            throw new FormularioAlreadyExistsException("Email ou CPF já enviou formulario para este animal");
        }

        ZonedDateTime dataEnvio = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        FormularioEntity formularioEntity = FormularioEntity.builder()
                .animal(animalMapper.toEntity(animal))
                .organizacao(organizacaoMapper.toEntity(organizacao))
                .nomeAdotante(formularioRequestDTO.nomeAdotante())
                .email(formularioRequestDTO.email())
                .idade(formularioRequestDTO.idade())
                .telefone(formularioRequestDTO.telefone())
                .cpf(formularioRequestDTO.cpf())
                .status(StatusFormularioEnum.PENDENTE)
                .dataEnvio(dataEnvio.toLocalDateTime())
                .build();

        FormularioEntity saved = formularioRepository.save(formularioEntity);

        if (formularioRequestDTO.respostas() != null
                && !formularioRequestDTO.respostas().isEmpty()) {
            List<RespostasEntity> respostasEntityList = formularioRequestDTO.respostas().stream()
                    .map(respostaRequestDTO -> RespostasEntity.builder()
                            .formulario(saved)
                            .pergunta(PerguntaEntity.builder().id(respostaRequestDTO.idPergunta()).build())
                            .resposta(respostaRequestDTO.resposta())
                            .build()
                    ).toList();

            respostasRepository.saveAll(respostasEntityList);
        }

        Map<String, Object> orgTemplateModel = new HashMap<>();
        orgTemplateModel.put("nomeOrganizacao", organizacao.nome());
        orgTemplateModel.put("nomeAnimal", animal.nome());
        orgTemplateModel.put("tipoAnimal", animal.tipo().toString());
        orgTemplateModel.put("nomeAdotante", formularioRequestDTO.nomeAdotante());
        orgTemplateModel.put("emailAdotante", formularioRequestDTO.email());
        orgTemplateModel.put("telefoneAdotante", formularioRequestDTO.telefone());
        orgTemplateModel.put("idadeAdotante", formularioRequestDTO.idade());
        orgTemplateModel.put("dataEnvio", saved.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        orgTemplateModel.put("painelUrl", "https://main.d99l6n0fah3sa.amplifyapp.com/dashboard/");

        emailService.sendHtmlEmail(
                organizacao.email(),
                "Novo Formulário de Adoção Recebido",
                "novoFormularioRecebidoEmail",
                orgTemplateModel
        );

        Map<String, Object> adotanteTemplateModel = new HashMap<>();
        adotanteTemplateModel.put("nomeAdotante", formularioRequestDTO.nomeAdotante());
        adotanteTemplateModel.put("nomeAnimal", animal.nome());
        adotanteTemplateModel.put("tipoAnimal", animal.tipo().toString());
        adotanteTemplateModel.put("nomeOrganizacao", organizacao.nome());
        adotanteTemplateModel.put("emailOrganizacao", organizacao.email());
        adotanteTemplateModel.put("telefoneOrganizacao", organizacao.numero());
        emailService.sendHtmlEmail(
                formularioRequestDTO.email(),
                "Seu Formulário de Adoção foi Enviado com Sucesso",
                "formularioEnviadoConfirmacaoEmail",
                adotanteTemplateModel
        );

        notificationController.notifyOrganization(organizacao.id(), animal.nome());
    }


    @Override
    public List<FormularioResponseDTO> getFormulariosByAnimalId(Long animalId, Long tokenOrgId) {
        Animal animal = getAnimalByIdCase.execute(animalId);
        if (!tokenOrgId.equals(animal.organizacao().id())){
            throw new UnauthorizedAccessException("Você não tem permissão para acessar isso");
        }

        List<FormularioEntity> formularios = formularioRepository.findByAnimal_IdAndStatusInOrderByStatusAscDataEnvioAsc(animalId, List.of(StatusFormularioEnum.APROVADO, StatusFormularioEnum.PENDENTE));

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
                    formulario.getStatus(),
                    formulario.getNomeAdotante(),
                    formulario.getEmail(),
                    formulario.getIdade(),
                    formulario.getTelefone(),
                    formulario.getCpf(),
                    formulario.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
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

        ZonedDateTime dataEnvio = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        formulario.setStatus(StatusFormularioEnum.APROVADO);
        formulario.setDataResposta(dataEnvio.toLocalDateTime());
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
    @Transactional
    public void recusarFormularioById(MensagemRecusaDTO mensagemRecusaDTO, Long id, Long tokenOrgId) {
        FormularioEntity formulario = formularioRepository.findById(id)
                .orElseThrow(() -> new FormularioNotFoundException("Formulário não existe"));

        if (!tokenOrgId.equals(formulario.getOrganizacao().getId())){
            throw new UnauthorizedAccessException("Você não tem permissão para acessar isso");
        } else if(formulario.getStatus().equals(StatusFormularioEnum.APROVADO)
                || formulario.getStatus().equals(StatusFormularioEnum.RECUSADO)){
            throw new UnauthorizedAccessException("O formulário já foi processado anteriormente");
        }

        ZonedDateTime dataEnvio = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        formulario.setStatus(StatusFormularioEnum.RECUSADO);
        formulario.setDataResposta(dataEnvio.toLocalDateTime());
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

        respostasRepository.deleteByFormulario_Id(formulario.getId());
        formularioRepository.deleteById(formulario.getId());
    }

    @Override
    public void deleteFormularioById(Long id, Long tokenOrgId) {
        FormularioEntity formularioEntity = formularioRepository.findById(id)
                .orElseThrow(() -> new FormularioNotFoundException(id.toString()));

        if(!tokenOrgId.equals(formularioEntity.getOrganizacao().getId())){
            throw new UnauthorizedAccessException("VocÊ não tem permissão para excluir esse formulário");
        }

        if(formularioEntity.getStatus() == StatusFormularioEnum.PENDENTE){
            throw new UnauthorizedAccessException("É necessário recusar ou aprovar o formulário");
        }

        formularioRepository.deleteById(id);
        respostasRepository.deleteByFormulario_Id(id);
    }
}
