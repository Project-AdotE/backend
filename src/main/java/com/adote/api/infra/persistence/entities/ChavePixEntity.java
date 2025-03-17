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
@Table(name = "chaves_pix")
public class ChavePixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @Column(unique = true, nullable = false)
    private String chave;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoEntity organizacao;
}
