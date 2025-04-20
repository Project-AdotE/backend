package com.adote.api.core.usecases.passwordToken.post;

import com.adote.api.core.gateway.PasswordTokenGateway;
import com.adote.api.infra.dtos.passwordToken.request.NovaSenhaRequestDTO;

public class ResetUserPasswordCaseImpl implements ResetUserPasswordCase {

    private final PasswordTokenGateway passwordTokenGateway;

    public ResetUserPasswordCaseImpl(PasswordTokenGateway resetPasswordTokenGateway) {
        this.passwordTokenGateway = resetPasswordTokenGateway;
    }

    @Override
    public void execute(NovaSenhaRequestDTO NewPasswordDTO) {
        passwordTokenGateway.resetPassword(NewPasswordDTO);
    }
}
