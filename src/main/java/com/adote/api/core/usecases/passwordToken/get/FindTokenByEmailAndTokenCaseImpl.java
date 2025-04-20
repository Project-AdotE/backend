package com.adote.api.core.usecases.passwordToken.get;

import com.adote.api.core.entities.PasswordToken;
import com.adote.api.core.exceptions.passwordToken.TokenNotFoundException;
import com.adote.api.core.gateway.PasswordTokenGateway;

public class FindTokenByEmailAndTokenCaseImpl implements FindTokenByEmailAndTokenCase {

    private final PasswordTokenGateway passwordTokenGateway;

    public FindTokenByEmailAndTokenCaseImpl(PasswordTokenGateway resetPasswordTokenGateway) {
        this.passwordTokenGateway = resetPasswordTokenGateway;
    }

    @Override
    public PasswordToken execute(String email, String token) {
        return passwordTokenGateway.FindTokenByEmailAndToken(email, token)
                .orElseThrow(TokenNotFoundException::new);
    }
}
