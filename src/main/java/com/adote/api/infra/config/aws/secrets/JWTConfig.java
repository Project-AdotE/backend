package com.adote.api.infra.config.aws.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {

    private final String secret;

    @Autowired
    public JWTConfig(AWSSecretManagerService awsSecretManagerService) {
        JsonNode secretNode = awsSecretManagerService.getSecret("secret-jwt-api-adote");
        this.secret = secretNode.get("jwtSecret").asText();
    }

    public String getSecret() {
        return secret;
    }
}