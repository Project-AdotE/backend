package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.exceptions.oraganizacao.CnpjAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.EmailAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.gateway.OrganizacaoGateway;
import com.adote.api.infra.filters.organizacao.OrganizacaoFilter;
import com.adote.api.infra.filters.organizacao.OrganizacaoSpecification;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrganizacaoRepositoryGateway implements OrganizacaoGateway {

    private final PasswordEncoder passwordEncoder;

    private final OrganizacaoMapper organizacaoMapper;
    private final OrganizacaoRepository organizacaoRepository;

    @Override
    public Page<Organizacao> getAllorganizacoes(OrganizacaoFilter filter, Pageable pageable) {
        OrganizacaoSpecification spec = new OrganizacaoSpecification(filter);
        Page<OrganizacaoEntity> organizacoes = organizacaoRepository.findAll(spec, pageable);
        return organizacoes.map(organizacaoMapper::toOrganizacao);
    }

    @Override
    public Optional<Organizacao> getOrganizacaoByCnpj(String cnpj) {
        Optional<OrganizacaoEntity> organizacaoEntity = organizacaoRepository.findByCnpj(cnpj);
        return organizacaoEntity.map(organizacaoMapper::toOrganizacao);
    }

    @Override
    public Optional<Organizacao> getOrganizacaoByEmail(String email) {
        Optional<OrganizacaoEntity> organizacaoEntity = organizacaoRepository.findByEmail(email);
        return organizacaoEntity.map(organizacaoMapper::toOrganizacao);
    }

    @Override
    public Organizacao createOrganizacao(Organizacao organizacao) {
        if (organizacaoRepository.findByCnpj(organizacao.cnpj()).isPresent()) {
            throw new CnpjAlreadyExistsException(organizacao.cnpj());
        }

        if (organizacaoRepository.findByEmail(organizacao.email()).isPresent()) {
            throw new EmailAlreadyExistsException(organizacao.email());
        }

        OrganizacaoEntity organizacaoEntity = organizacaoMapper.toEntity(organizacao);
        organizacaoEntity.setSenha(passwordEncoder.encode(organizacaoEntity.getSenha()));
        return organizacaoMapper.toOrganizacao(organizacaoRepository.save(organizacaoEntity));
    }

    @Override
    public Optional<Organizacao> getOrganizacaoById(Long id) {
        Optional<OrganizacaoEntity> organizacaoEntity = organizacaoRepository.findById(id);
        return organizacaoEntity.map(organizacaoMapper::toOrganizacao);
    }

    @Override
    public void deleteOrganizacaoById(Long id) {
        if(organizacaoRepository.existsById(id)) {
            organizacaoRepository.deleteById(id);
        }else{
            throw new OrganizacaoNotFoundException(id.toString());
        }
    }
}
