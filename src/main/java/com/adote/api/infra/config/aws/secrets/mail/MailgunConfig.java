package com.adote.api.infra.config.aws.secrets.mail;

import com.adote.api.infra.config.aws.secrets.AWSSecretManagerService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MailgunConfig {

    private final AWSSecretManagerService awsSecretManagerService;

    @Bean
    public MailgunProperties mailgunProperties() {
        JsonNode secret = awsSecretManagerService.getSecret("Email-app-password");

        return MailgunProperties.builder()
                .apiKey(secret.get("mailgun.api.key").asText())
                .domain(secret.get("mailgun.domain").asText())
                .from(secret.get("mailgun.from").asText())
                .build();
    }
}