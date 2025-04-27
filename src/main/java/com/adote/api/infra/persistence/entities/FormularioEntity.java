package com.adote.api.infra.persistence.entities;

import com.adote.api.core.Enums.StatusFormularioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "formularios")
public class FormularioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_animal",
                nullable = false)
    private AnimalEntity animal;

    @ManyToOne
    @JoinColumn(name = "id_org",
            nullable = false)
    private OrganizacaoEntity organizacao;

    @Column(name = "nome_adotante", nullable = false)
    private String nomeAdotante;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "idade", nullable = false)
    private Long idade;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusFormularioEnum status;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;

}
