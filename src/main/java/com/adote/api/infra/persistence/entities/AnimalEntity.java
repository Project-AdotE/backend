package com.adote.api.infra.persistence.entities;

import com.adote.api.core.Enums.PorteEnum;
import com.adote.api.core.Enums.SexoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "porte", nullable = false)
    private PorteEnum porte;

    @Column(name = "vacinado", nullable = false)
    private Boolean vacinado;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoEntity organizacao;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FotoAnimalEntity> fotos;
}
