package com.adote.api.infra.presentation;

import com.adote.api.core.entities.Pergunta;
import com.adote.api.core.usecases.perguntas.GetAllPerguntasUseCase;
import com.adote.api.infra.dtos.perguntas.PerguntaResponseDTO;
import com.adote.api.infra.mappers.PerguntaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pergunta")
@RequiredArgsConstructor
public class PerguntasController {

    private final GetAllPerguntasUseCase getAllPerguntasUseCase;

    private final PerguntaMapper perguntaMapper;

    @GetMapping
    public ResponseEntity<List<PerguntaResponseDTO>> getAllPerguntas() {
        List<Pergunta> perguntas = getAllPerguntasUseCase.execute();
        return ResponseEntity.ok(perguntas.stream()
                .map(perguntaMapper::toResponseDTO)
                .collect(Collectors.toList())
        );
    }

}
