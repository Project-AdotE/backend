package com.adote.api.core.usecases.perguntas;

import com.adote.api.core.entities.Pergunta;

import java.util.List;

public interface GetAllPerguntasUseCase {
    List<Pergunta> execute();
}
