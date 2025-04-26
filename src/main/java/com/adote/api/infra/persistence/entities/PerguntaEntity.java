package com.adote.api.infra.persistence.entities;

import com.adote.api.core.Enums.TipoPerguntaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "perguntas")
public class PerguntaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pergunta", nullable = false, unique = true)
    private String pergunta;

    @Column(name = "posicao", nullable = false, unique = true)
    private Long posicao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPerguntaEnum tipo;

}
