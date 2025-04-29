package com.adote.api.infra.config.websocket;

import com.adote.api.core.entities.Organizacao;
import com.adote.api.infra.config.auth.JWTUserData;
import com.adote.api.infra.config.auth.TokenService;
import com.adote.api.infra.persistence.entities.OrganizacaoEntity;
import com.adote.api.infra.persistence.repositories.OrganizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;
    private final OrganizacaoRepository organizacaoRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand().name())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Optional<JWTUserData> jwtData = tokenService.verifyToken(token);

                if (jwtData.isPresent()) {
                    JWTUserData userData = jwtData.get();

                    OrganizacaoEntity organizacao = organizacaoRepository
                            .findByEmail(userData.email())
                            .orElse(null);

                    if (organizacao != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(organizacao, null, null);
                        accessor.setUser(authentication);
                    }
                }
            }
        }

        return message;
    }
}