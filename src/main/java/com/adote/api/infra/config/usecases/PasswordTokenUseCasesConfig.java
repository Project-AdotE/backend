package com.adote.api.infra.config.usecases;

import com.adote.api.core.usecases.passwordToken.get.FindTokenByEmailAndTokenCase;
import com.adote.api.core.usecases.passwordToken.get.FindTokenByEmailAndTokenCaseImpl;
import com.adote.api.core.usecases.passwordToken.post.*;
import com.adote.api.infra.gateway.PasswordTokenRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PasswordTokenUseCasesConfig {

    private final PasswordTokenRepositoryGateway passwordTokenRepositoryGateway;

    @Bean
    public GeneratePasswordTokenUseCase generatePasswordTokenUseCase() {
        return new GeneratePasswordTokenUseCaseImpl(passwordTokenRepositoryGateway);
    }

    @Bean
    public FindTokenByEmailAndTokenCase findTokenByEmailAndTokenCase() {
        return new FindTokenByEmailAndTokenCaseImpl(passwordTokenRepositoryGateway);
    }

    @Bean
    public ResetUserPasswordCase resetUserPasswordCase() {
        return new ResetUserPasswordCaseImpl(passwordTokenRepositoryGateway);
    }

    @Bean
    public VerifyResetPasswordTokenUseCase verifyResetPasswordTokenUseCase() {
        return new VerifyResetPasswordTokenUseCaseImpl(passwordTokenRepositoryGateway);
    }
}
