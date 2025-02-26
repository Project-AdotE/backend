package com.adote.api.core.usecases.fotoAnimal.get;

import com.adote.api.core.entities.FotoAnimal;

import java.util.List;

public interface GetFotoByAnimalIdCase {

    List<FotoAnimal> execute(Long id);

}
