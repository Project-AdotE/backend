package com.adote.api.core.usecases.passwordToken.post;

import com.adote.api.infra.dtos.passwordToken.request.NovaSenhaRequestDTO;

public interface ResetUserPasswordCase {
    void execute(NovaSenhaRequestDTO NewPasswordDTO);
}
