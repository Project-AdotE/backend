package com.adote.api.core.usecases.passwordToken.post;

import com.adote.api.core.gateway.PasswordTokenGateway;

public class GeneratePasswordTokenUseCaseImpl implements GeneratePasswordTokenUseCase {

    private final PasswordTokenGateway resetPasswordTokenGateway;

    public GeneratePasswordTokenUseCaseImpl(PasswordTokenGateway resetPasswordTokenGateway) {
        this.resetPasswordTokenGateway = resetPasswordTokenGateway;
    }

    @Override
    public void execute(String email) {
        resetPasswordTokenGateway.GenerateResetPasswordToken(email);
    }

}
