package com.adote.api.infra.filters.animal;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AnimalSpecification implements Specification<AnimalEntity> {

    private final AnimalFilter filter;

    @Override
    public Predicate toPredicate(Root<AnimalEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isFalse(root.get("adotado")));

        if (filter.getTipo() != null) {
            predicates.add(cb.equal(root.get("tipo"), filter.getTipo()));
        }

        if (filter.getIdade() != null) {
            predicates.add(cb.equal(root.get("idade"), filter.getIdade()));
        }

        if (filter.getPorte() != null) {
            predicates.add(cb.equal(root.get("porte"), filter.getPorte()));
        }

        if (filter.getSexo() != null) {
            predicates.add(cb.equal(root.get("sexo"), filter.getSexo()));
        }

        if (filter.getOrganizacaoId() != null) {
            predicates.add(cb.equal(root.get("organizacao").get("id"), filter.getOrganizacaoId()));
        }

        // JOIN com organização → endereço
        Join<Object, Object> organizacaoJoin = root.join("organizacao");
        Join<Object, Object> enderecoJoin = organizacaoJoin.join("endereco");

        if (filter.getEstado() != null && !filter.getEstado().isBlank()) {
            predicates.add(cb.equal(cb.lower(enderecoJoin.get("estado")), filter.getEstado().toLowerCase()));
        }

        if (filter.getCidade() != null && !filter.getCidade().isBlank()) {
            predicates.add(cb.equal(cb.lower(enderecoJoin.get("cidade")), filter.getCidade().toLowerCase()));
        }

        query.orderBy(cb.desc(root.get("createdAt")));

        return cb.and(predicates.toArray(new Predicate[0]));
    }

}