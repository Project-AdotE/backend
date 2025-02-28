package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAnimaisByOrganizationId {

    Page<Animal> execute(Long id, Pageable pageable);
}
