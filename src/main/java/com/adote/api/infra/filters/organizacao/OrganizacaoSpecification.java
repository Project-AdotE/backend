package com.adote.api.infra.filters.organizacao;

import com.adote.api.infra.persistence.entities.EnderecoOrganizacaoEntity;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrganizacaoSpecification implements Specification<OrganizacaoEntity> {

    private final OrganizacaoFilter organizacaoFilter;

    @Override
    public Predicate toPredicate(Root<OrganizacaoEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<OrganizacaoEntity, EnderecoOrganizacaoEntity> enderecoJoin = root.join("endereco", JoinType.INNER);

        if (organizacaoFilter.getCidade() != null) {
            predicates.add(criteriaBuilder.equal(enderecoJoin.get("cidade"), organizacaoFilter.getCidade()));
        }

        if (organizacaoFilter.getEstado() != null) {
            predicates.add(criteriaBuilder.equal(enderecoJoin.get("estado"), organizacaoFilter.getEstado()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
