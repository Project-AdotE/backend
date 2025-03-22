package com.adote.api.infra.config.auth;

import com.adote.api.infra.config.aws.secrets.JWTConfig;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final JWTConfig jwtConfig;

    public String generateToken(OrganizacaoEntity organizacao) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        return JWT.create()
                .withSubject(organizacao.getEmail())
                .withClaim("organizacao_id", organizacao.getId())
                .withClaim("organizacao_name", organizacao.getNome())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 24))
                .withIssuer("Adote API")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());

            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("Adote API")
                    .build()
                    .verify(token);

            if(!jwt.getIssuer().equals("Adote API")){
                throw new JWTVerificationException("Token invalido");
            }

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
