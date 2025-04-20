package com.adote.api.core.usecases.passwordToken.post;

public interface VerifyResetPasswordTokenUseCase {
    Boolean execute(String email, String token);
}
