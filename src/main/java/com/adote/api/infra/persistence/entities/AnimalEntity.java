package com.adote.api.infra.persistence.entities;

import com.adote.api.core.Enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Builder
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
    @Column(name = "tipo", nullable = false)
    private TipoAnimalEnum tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "porte", nullable = false)
    private PorteEnum porte;

    @Enumerated(EnumType.STRING)
    @Column(name = "idade", nullable = false)
    private IdadeEnum idade;

    @Column(name = "vacinado", nullable = false)
    private Boolean vacinado;

    @Column(name = "castrado", nullable = false)
    private Boolean castrado;

    @Column(name = "vermifugado", nullable = false)
    private Boolean vermifugado;

    @Column(name = "srd", nullable = false)
    private Boolean srd;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "organizacao_id",
                nullable = false)
    private OrganizacaoEntity organizacao;

    @OneToMany(mappedBy = "animal",
                cascade = CascadeType.ALL,
                orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FotoAnimalEntity> fotos;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
