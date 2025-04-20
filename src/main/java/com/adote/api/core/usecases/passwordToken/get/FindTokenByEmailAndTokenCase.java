package com.adote.api.core.usecases.passwordToken.get;

import com.adote.api.core.entities.PasswordToken;

public interface FindTokenByEmailAndTokenCase {
    PasswordToken execute(String email, String token);
}
