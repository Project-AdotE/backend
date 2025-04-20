package com.adote.api.core.usecases.passwordToken.post;

import com.adote.api.core.gateway.PasswordTokenGateway;

public class VerifyResetPasswordTokenUseCaseImpl implements VerifyResetPasswordTokenUseCase {


    private final PasswordTokenGateway passwordTokenGateway;

    public VerifyResetPasswordTokenUseCaseImpl(PasswordTokenGateway resetPasswordTokenGateway) {
        this.passwordTokenGateway = resetPasswordTokenGateway;
    }

    @Override
    public Boolean execute(String email, String token) {
        return passwordTokenGateway.VerifyResetPasswordToken(email, token);
    }
}
