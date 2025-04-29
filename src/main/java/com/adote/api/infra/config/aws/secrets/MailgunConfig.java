package com.adote.api.infra.config.aws.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Optional;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailgunConfig {

    private final AWSSecretManagerService awsSecretManagerService;

    @Bean
    public JavaMailSender javaMailSender() {
        JsonNode secret = awsSecretManagerService.getSecret("Email-app-password");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(getJsonValue(secret, "spring.mail.host"));
        mailSender.setPort(Integer.parseInt(getJsonValue(secret, "spring.mail.port")));
        mailSender.setUsername(getJsonValue(secret, "spring.mail.username"));
        mailSender.setPassword(getJsonValue(secret, "spring.mail.password"));
        mailSender.setProtocol(getJsonValue(secret, "spring.mail.protocol"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", getJsonValue(secret, "spring.mail.protocol"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    private String getJsonValue(JsonNode node, String key) {
        return Optional.ofNullable(node.get(key))
                .map(JsonNode::asText)
                .orElseThrow(() -> new IllegalStateException("Campo ausente no segredo: " + key));
    }
}