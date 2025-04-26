package com.adote.api.infra.persistence.entities;

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
@Table(name = "respostas")
public class RespostasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_formulario",
                nullable = false)
    private FormularioEntity formulario;

    @ManyToOne
    @JoinColumn(name = "id_pergunta",
            nullable = false)
    private PerguntaEntity pergunta;

    @Column(name = "resposta", nullable = false)
    private String resposta;

}
