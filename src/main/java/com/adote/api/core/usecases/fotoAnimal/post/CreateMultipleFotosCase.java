package com.adote.api.core.usecases.fotoAnimal.post;

import com.adote.api.core.entities.FotoAnimal;

import java.util.List;

public interface CreateMultipleFotosCase {

    List<FotoAnimal> execute(List<FotoAnimal> fotos);

}
