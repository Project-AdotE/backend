package com.adote.api.infra.filters.animal;

import com.adote.api.infra.persistence.entities.AnimalEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

        query.orderBy(cb.desc(root.get("createdAt")));

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}