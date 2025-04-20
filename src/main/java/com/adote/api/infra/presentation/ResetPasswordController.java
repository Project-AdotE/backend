package com.adote.api.infra.presentation;

import com.adote.api.core.usecases.passwordToken.post.GeneratePasswordTokenUseCase;
import com.adote.api.core.usecases.passwordToken.post.ResetUserPasswordCase;
import com.adote.api.core.usecases.passwordToken.post.VerifyResetPasswordTokenUseCase;
import com.adote.api.infra.dtos.passwordToken.request.NovaSenhaRequestDTO;
import com.adote.api.infra.dtos.passwordToken.request.ResetSenhaRequestDTO;
import com.adote.api.infra.dtos.passwordToken.request.VerificaPassTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset-password")
@RequiredArgsConstructor
public class ResetPasswordController {


    private final GeneratePasswordTokenUseCase generateResetPasswordTokenUseCase;
    private final VerifyResetPasswordTokenUseCase verifyResetPasswordTokenUseCase;
    private final ResetUserPasswordCase resetUserPasswordCase;

    /**
     * Gera e envia um token para redefinição de senha.
     */
    @PostMapping("/request")
    public ResponseEntity<String> requestResetPassword(@RequestBody @Valid ResetSenhaRequestDTO requestDTO) {
        generateResetPasswordTokenUseCase.execute(requestDTO.email());
        return ResponseEntity.ok("Código de redefinição enviado para o e-mail.");
    }

    /**
     * Verifica se o token está correto.
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyResetPassword(@RequestBody @Valid VerificaPassTokenDTO verifyDTO) {
        boolean isValid = verifyResetPasswordTokenUseCase.execute(verifyDTO.email(), verifyDTO.token());
        if (isValid) {
            return ResponseEntity.ok("Token válido. Você pode redefinir sua senha.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
    }

    /**
     * Permite redefinir a senha caso o token seja válido.
     */
    @PostMapping("/new-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid NovaSenhaRequestDTO newPasswordDTO) {
        resetUserPasswordCase.execute(newPasswordDTO);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
