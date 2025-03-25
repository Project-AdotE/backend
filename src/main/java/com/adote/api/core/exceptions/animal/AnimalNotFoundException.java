package com.adote.api.core.exceptions.animal;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String message) {
        super("Animal não encontrado com: " + message);
    }
}
