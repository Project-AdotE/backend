package com.adote.api.core.usecases.perguntas;

import com.adote.api.core.entities.Pergunta;
import com.adote.api.core.gateway.PerguntaGateway;

import java.util.List;

public class GetAllPerguntasUseCaseImpl implements GetAllPerguntasUseCase {

    private final PerguntaGateway perguntaGateway;

    public GetAllPerguntasUseCaseImpl(PerguntaGateway perguntaGateway) {
        this.perguntaGateway = perguntaGateway;
    }

    @Override
    public List<Pergunta> execute() {
        return perguntaGateway.getAllPerguntas();
    }
}
