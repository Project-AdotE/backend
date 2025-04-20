package com.adote.api.infra.config.aws.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Optional;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private AWSSecretManagerService awsSecretManagerService;

    @Bean
    public JavaMailSender javaMailSender() {
        JsonNode secret = awsSecretManagerService.getSecret("Email-app-password");

        String host = getJsonValue(secret, "host");
        int port = Integer.parseInt(getJsonValue(secret, "port"));
        String username = getJsonValue(secret, "username");
        String password = getJsonValue(secret, "password");
        String auth = getJsonValue(secret, "auth");
        String starttls = getJsonValue(secret, "starttls");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);

        return mailSender;
    }

    private String getJsonValue(JsonNode node, String key) {
        return Optional.ofNullable(node.get(key))
                .map(JsonNode::asText)
                .orElseThrow(() -> new IllegalStateException("Campo ausente no segredo: " + key));
    }
}