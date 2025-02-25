package com.adote.api.infra.config.auth;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String name, String email) {
}
