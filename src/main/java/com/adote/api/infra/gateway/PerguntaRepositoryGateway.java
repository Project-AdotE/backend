package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Pergunta;
import com.adote.api.core.gateway.PerguntaGateway;
import com.adote.api.infra.mappers.PerguntaMapper;
import com.adote.api.infra.persistence.entities.PerguntaEntity;
import com.adote.api.infra.persistence.repositories.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PerguntaRepositoryGateway implements PerguntaGateway {

    private final PerguntaRepository perguntaRepository;

    private final PerguntaMapper perguntaMapper;

    @Override
    public List<Pergunta> getAllPerguntas() {
        List<PerguntaEntity> entityList = perguntaRepository.findAll();
        return entityList.stream()
                .map(perguntaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
