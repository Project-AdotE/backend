package com.adote.api.infra.persistence.repositories;

import com.adote.api.infra.persistence.entities.PasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordTokenEntity, Long> {
    Optional<PasswordTokenEntity> findByEmailAndToken(String email, String token);

    int deleteByExpirationTimeBefore(LocalDateTime expirationTimeBefore);
}
