package com.adote.api.core.gateway;

import com.adote.api.core.entities.PasswordToken;
import com.adote.api.infra.dtos.passwordToken.request.NovaSenhaRequestDTO;

import java.util.Optional;

public interface PasswordTokenGateway {

    Optional<PasswordToken> FindTokenByEmailAndToken(String email, String token);

    void GenerateResetPasswordToken(String email);

    Boolean VerifyResetPasswordToken(String email, String token);

    void resetPassword(NovaSenhaRequestDTO NewPasswordDTO);
}
