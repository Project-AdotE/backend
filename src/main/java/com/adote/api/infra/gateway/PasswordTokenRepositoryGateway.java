package com.adote.api.infra.gateway;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.core.entities.PasswordToken;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import com.adote.api.core.exceptions.passwordToken.NewPasswordSameAsOldException;
import com.adote.api.core.exceptions.passwordToken.TokenNotFoundException;
import com.adote.api.core.gateway.PasswordTokenGateway;
import com.adote.api.infra.dtos.passwordToken.request.NovaSenhaRequestDTO;
import com.adote.api.infra.mappers.OrganizacaoMapper;
import com.adote.api.infra.mappers.PasswordTokenMapper;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.entities.PasswordTokenEntity;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import com.adote.api.infra.persistence.repositories.PasswordTokenRepository;
import com.adote.api.infra.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordTokenRepositoryGateway implements PasswordTokenGateway {

    private final PasswordTokenRepository passwordTokenRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final PasswordTokenMapper passwordTokenMapper;
    private final OrganizacaoMapper organizacaoMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void GenerateResetPasswordToken(String email) {
        Optional<OrganizacaoEntity> organizacaoEntityOptional = organizacaoRepository.findByEmail(email);
        if (organizacaoEntityOptional.isEmpty()) {
            throw new OrganizacaoNotFoundException(email);
        }
        Organizacao org = organizacaoMapper.toOrganizacao(organizacaoEntityOptional.get());

        SecureRandom secureRandom = new SecureRandom();
        String token = String.format("%06d", secureRandom.nextInt(1_000_000));

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        PasswordToken resetToken = new PasswordToken(email, token, expirationTime);

        passwordTokenRepository.save(passwordTokenMapper.toEntity(resetToken));

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("username", org.nome());
        templateModel.put("token", token);

        emailService.sendHtmlEmail("felipewai.dev@gmail.com", "Reset Password Token", templateModel);
    }

    @Override
    public Boolean VerifyResetPasswordToken(String email, String token) {
        LocalDateTime now = LocalDateTime.now();

        PasswordToken resetPasswordToken = FindTokenByEmailAndToken(email, token)
                .orElseThrow(TokenNotFoundException::new);

        return now.isBefore(resetPasswordToken.expirationTime()) && !resetPasswordToken.used();
    }

    @Override
    public Optional<PasswordToken> FindTokenByEmailAndToken(String email, String token) {
        Optional<PasswordTokenEntity> tokenEntity = passwordTokenRepository.findByEmailAndToken(email, token);
        return tokenEntity.map(passwordTokenMapper::toDomain);
    }

    @Override
    public void resetPassword(NovaSenhaRequestDTO newPasswordDTO) {

        Optional<OrganizacaoEntity> organizacaoEntityOptional = organizacaoRepository.findByEmail(newPasswordDTO.email());
        if (organizacaoEntityOptional.isEmpty()) {
            throw new OrganizacaoNotFoundException(newPasswordDTO.email());
        }

        OrganizacaoEntity org = organizacaoEntityOptional.get();
        PasswordToken resetPasswordToken = FindTokenByEmailAndToken(newPasswordDTO.email(), newPasswordDTO.token())
                .orElseThrow(TokenNotFoundException::new);

        if (LocalDateTime.now().isAfter(resetPasswordToken.expirationTime()) || resetPasswordToken.used()) {
            throw new TokenNotFoundException();
        }

        PasswordTokenEntity resetPasswordTokenEntity = passwordTokenMapper.toEntity(resetPasswordToken);
        resetPasswordTokenEntity.setUsed(true);
        passwordTokenRepository.save(resetPasswordTokenEntity);

        if (passwordEncoder.matches(newPasswordDTO.novaSenha(), org.getSenha())) {
            throw new NewPasswordSameAsOldException();
        }


        org.setSenha(passwordEncoder.encode(newPasswordDTO.novaSenha()));
        organizacaoRepository.save(org);
    }

    @Scheduled(fixedRate = 86400000)
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        int deletedTokens = passwordTokenRepository.deleteByExpirationTimeBefore(now);
        System.out.println("Tokens expirados removidos: " + deletedTokens);
    }
}
