package com.adote.api.infra.persistence.entities;

import com.adote.api.core.Enums.TipoChaveEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoChaveEnum tipo;

    @Column(unique = true, nullable = false)
    private String chave;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoEntity organizacao;
}
