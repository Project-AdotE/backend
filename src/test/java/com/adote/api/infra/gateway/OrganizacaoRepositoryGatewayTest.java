package com.adote.api.infra.gateway;

import com.adote.api.core.entities.EnderecoOrganizacao;
import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.oraganizacao.CnpjAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.EmailAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoByCnpjCaseImpl;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoByEmailUseCaseImpl;
import com.adote.api.core.usecases.organizacao.get.GetOrganizacaoByIdImpl;
import com.adote.api.infra.gateway.OrganizacaoRepositoryGateway;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.EnderecoOrganizacaoEntity;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrganizacaoRepositoryGatewayTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrganizacaoMapper organizacaoMapper;

    @Mock
    private OrganizacaoRepository organizacaoRepository;

    @InjectMocks
    private OrganizacaoRepositoryGateway gateway;

    @Test
    @DisplayName("Deve Encontrar uma organização pelo cnpj corretamente")
    void getOrganizacaoByCnpjSuccess() {
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();
        Organizacao organizacao = criarOrganizacao();

        when(organizacaoRepository.findByCnpj(organizacaoEntity.getCnpj())).thenReturn(Optional.of(organizacaoEntity));
        when(organizacaoMapper.toOrganizacao(organizacaoEntity)).thenReturn(organizacao);

        Optional<Organizacao> response = gateway.getOrganizacaoByCnpj(organizacaoEntity.getCnpj());

        assertTrue(response.isPresent());
        assertEquals(organizacao, response.get());

        verify(organizacaoRepository, times(1)).findByCnpj(organizacaoEntity.getCnpj());
        verify(organizacaoMapper, times(1)).toOrganizacao(organizacaoEntity);
    }

    @Test
    @DisplayName("Deve retornar erro ao encontrar uma organização pelo cnpj")
    void getOrganizacaoByCnpjError() {
        when(organizacaoRepository.findByCnpj(anyString())).thenReturn(Optional.empty());

        GetOrganizacaoByCnpjCaseImpl getOrganizacaoByEmailUseCase = new GetOrganizacaoByCnpjCaseImpl(gateway);

        assertThrows(OrganizacaoNotFoundException.class, () -> getOrganizacaoByEmailUseCase.execute(anyString()));

        verify(organizacaoRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve Encontrar uma organização pelo email corretamente")
    void getOrganizacaoByEmailSuccess() {
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();
        Organizacao organizacao = criarOrganizacao();

        when(organizacaoRepository.findByEmail(organizacaoEntity.getEmail())).thenReturn(Optional.of(organizacaoEntity));
        when(organizacaoMapper.toOrganizacao(organizacaoEntity)).thenReturn(organizacao);

        Optional<Organizacao> response = gateway.getOrganizacaoByEmail(organizacaoEntity.getEmail());

        assertTrue(response.isPresent());
        assertEquals(organizacao, response.get());

        verify(organizacaoRepository, times(1)).findByEmail(organizacaoEntity.getEmail());
        verify(organizacaoMapper, times(1)).toOrganizacao(organizacaoEntity);
    }

    @Test
    @DisplayName("Deve retornar erro ao encontrar uma organização pelo email")
    void getOrganizacaoByEmailError() {
        when(organizacaoRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        GetOrganizacaoByEmailUseCaseImpl getOrganizacaoByEmailUseCase = new GetOrganizacaoByEmailUseCaseImpl(gateway);

        assertThrows(OrganizacaoNotFoundException.class, () -> getOrganizacaoByEmailUseCase.execute(anyString()));

        verify(organizacaoRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve criar uma nova organização com sucesso")
    void createOrganizacaoSuccess() {
        Organizacao organizacaoDomain = criarOrganizacao();
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();

        Organizacao organizacaoDomainComSenhaCriptografada = new Organizacao(
                organizacaoDomain.id(),
                organizacaoDomain.nome(),
                organizacaoDomain.numero(),
                organizacaoDomain.cnpj(),
                organizacaoDomain.endereco(),
                organizacaoDomain.chavesPix(),
                organizacaoDomain.email(),
                "senhaCriptografada"
        );

        OrganizacaoEntity savedOrganizacaoEntity = criarOrganizacaoEntity();
        savedOrganizacaoEntity.setSenha("senhaCriptografada");

        when(organizacaoRepository.findByCnpj(organizacaoDomain.cnpj())).thenReturn(Optional.empty());
        when(organizacaoRepository.findByEmail(organizacaoDomain.email())).thenReturn(Optional.empty());

        when(organizacaoMapper.toEntity(organizacaoDomain)).thenReturn(organizacaoEntity);
        when(passwordEncoder.encode("minhaSenhaSegura")).thenReturn("senhaCriptografada");
        when(organizacaoRepository.save(organizacaoEntity)).thenReturn(savedOrganizacaoEntity);
        when(organizacaoMapper.toOrganizacao(savedOrganizacaoEntity)).thenReturn(organizacaoDomainComSenhaCriptografada);

        Organizacao response = gateway.createOrganizacao(organizacaoDomain);

        assertEquals(organizacaoEntity.getId(), response.id());
        assertEquals(organizacaoEntity.getCnpj(), response.cnpj());
        assertEquals(organizacaoEntity.getEmail(), response.email());
        assertEquals(organizacaoEntity.getNome(), response.nome());
        assertEquals("senhaCriptografada", response.senha());
        assertEquals(organizacaoEntity.getEndereco().getCep(), response.endereco().cep());
        assertEquals(organizacaoEntity.getEndereco().getRua(), response.endereco().rua());
        assertEquals(organizacaoEntity.getEndereco().getNumero(), response.endereco().numero());
        assertEquals(organizacaoEntity.getEndereco().getCidade(), response.endereco().cidade());
        assertEquals(organizacaoEntity.getEndereco().getEstado(), response.endereco().estado());
        assertEquals(organizacaoEntity.getChavesPix().size(), response.chavesPix().size());

        verify(organizacaoRepository, times(1)).findByCnpj(organizacaoDomain.cnpj());
        verify(organizacaoRepository, times(1)).findByEmail(organizacaoDomain.email());
        verify(passwordEncoder, times(1)).encode("minhaSenhaSegura");
        verify(organizacaoRepository, times(1)).save(organizacaoEntity);
    }

    @Test
    @DisplayName("Deve retornar CNPJ existente ao criar a organização")
    void createOrganizacaoErrorCNPJExistente() {
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();
        Organizacao organizacaoDomain = criarOrganizacao();

        when(organizacaoRepository.findByCnpj(organizacaoEntity.getCnpj())).thenReturn(Optional.of(organizacaoEntity));

        CnpjAlreadyExistsException exception = assertThrows(CnpjAlreadyExistsException.class,
                () -> gateway.createOrganizacao(organizacaoDomain));

        assertEquals("O cnpj 37.877.830/0001-01 já está registrado.", exception.getMessage());

        verify(organizacaoRepository, times(1)).findByCnpj(organizacaoEntity.getCnpj());
        verify(organizacaoRepository, never()).save(organizacaoEntity);
        verify(passwordEncoder, never()).encode("minhaSenhaSegura");
    }

    @Test
    @DisplayName("Deve retornar Email existente ao criar a organização")
    void createOrganizacaoErrorEmailExistente() {
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();
        Organizacao organizacaoDomain = criarOrganizacao();

        when(organizacaoRepository.findByEmail("felipe@email.com")).thenReturn(Optional.of(organizacaoEntity));

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class,
                () -> gateway.createOrganizacao(organizacaoDomain));

        assertEquals("O email felipe@email.com já foi registrado", exception.getMessage());

        verify(organizacaoRepository, times(1)).findByEmail("felipe@email.com");
        verify(organizacaoRepository, never()).save(organizacaoEntity);
    }

    @Test
    @DisplayName("Deve retornar a organização ao buscar pelo ID")
    void getOrganizacaoById() {
        OrganizacaoEntity organizacaoEntity = criarOrganizacaoEntity();
        Organizacao organizacaoDomain = criarOrganizacao();

        when(organizacaoRepository.findById(1L)).thenReturn(Optional.of(organizacaoEntity));
        when(organizacaoMapper.toOrganizacao(organizacaoEntity)).thenReturn(organizacaoDomain);

        Optional<Organizacao> response = gateway.getOrganizacaoById(1L);

        assertTrue(response.isPresent());
        assertEquals(organizacaoDomain, response.get());

        verify(organizacaoRepository, times(1)).findById(1L);
        verify(organizacaoMapper, times(1)).toOrganizacao(organizacaoEntity);
    }

    @Test
    @DisplayName("Deve retornar erro de ID não encontrado para organização ao buscar pelo ID")
    void getOrganizacaoByIdError() {
        when(organizacaoRepository.findById(2L)).thenReturn(Optional.empty());

        GetOrganizacaoByIdImpl getOrganizacaoById = new GetOrganizacaoByIdImpl(gateway);

        assertThrows(OrganizacaoNotFoundException.class, () -> getOrganizacaoById.execute(2L));

        verify(organizacaoRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Deve deletar com sucesso pelo ID")
    void deleteOrganizacaoByIdSuccess() {
        Long id = 1L;

        when(organizacaoRepository.existsById(id)).thenReturn(true);

        gateway.deleteOrganizacaoById(id);

        verify(organizacaoRepository, times(1)).existsById(id);
        verify(organizacaoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar excluir id não existente")
    void deleteOrganizacaoByIdError() {
        Long id = 1L;

        when(organizacaoRepository.existsById(id)).thenReturn(false);

        OrganizacaoNotFoundException exception = assertThrows(OrganizacaoNotFoundException.class,
                () -> gateway.deleteOrganizacaoById(id));

        assertEquals("Organização 1 não encontrada", exception.getMessage());

        verify(organizacaoRepository, times(1)).existsById(id);
        verify(organizacaoRepository, never()).deleteById(id);
    }


    private EnderecoOrganizacaoEntity criarEnderecoEntity() {
        return EnderecoOrganizacaoEntity.builder()
                .cep("11015-200")
                .rua("Avenida Ana Costa")
                .numero("1050")
                .cidade("Santos")
                .estado("SP")
                .build();
    }

    private EnderecoOrganizacao criarEndereco() {
        return new EnderecoOrganizacao(
                1L,
                "11015-200",
                "Avenida Ana Costa",
                "1050",
                "Santos",
                "SP"
        );
    }

    private Organizacao criarOrganizacao() {
        return new Organizacao(
                1L,
                "Adote Santos",
                "13999999999",
                "37.877.830/0001-01",
                criarEndereco(),
                List.of(),
                "felipe@email.com",
                "minhaSenhaSegura"
        );
    }

    private OrganizacaoEntity criarOrganizacaoEntity() {
        return OrganizacaoEntity.builder()
                .id(1L)
                .nome("Adote Santos")
                .numero("13999999999")
                .cnpj("37.877.830/0001-01")
                .email("felipe@email.com")
                .senha("minhaSenhaSegura")
                .endereco(criarEnderecoEntity())
                .animais(List.of())
                .chavesPix(List.of())
                .build();
    }
}