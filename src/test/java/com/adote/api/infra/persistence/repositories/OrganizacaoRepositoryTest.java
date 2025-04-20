package com.adote.api.infra.persistence.repositories;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.dtos.organizacao.request.OrganizacaoRequestDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class OrganizacaoRepositoryTest {

    @Test
    void getOrganizacaoEntityByEmail() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByCnpj() {
    }
}