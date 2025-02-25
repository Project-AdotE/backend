package com.adote.api.core.usecases.animal.get;

import com.adote.api.core.entities.Animal;

import java.util.List;

public interface GetAnimaisByOrganizationId {

    List<Animal> execute(Long id);

}
