package com.adote.api.core.usecases.passwordToken.post;

public interface GeneratePasswordTokenUseCase {
    void execute(String email);
}
