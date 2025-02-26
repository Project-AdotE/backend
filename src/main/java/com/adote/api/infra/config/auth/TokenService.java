package com.adote.api.infra.config.auth;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${adote.security.secret}")
    private String secret;

    public String generateToken(OrganizacaoEntity organizacao) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(organizacao.getEmail())
                .withClaim("organizacao_id", organizacao.getId())
                .withClaim("organizacao_name", organizacao.getNome())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 24))
                .withIssuer("API Adote")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .id(jwt.getClaim("organizacao_id").asLong())
                    .name(jwt.getClaim("organizacao_name").asString())
                    .email(jwt.getSubject())
                    .build());
        }catch(JWTVerificationException e){
            return Optional.empty();
        }
    }
}
