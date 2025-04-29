package com.adote.api.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    private final String SOURCE_EMAIL = "felipe.losadawai0@gmail.com";

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel) {
        try {
            Context context = new Context();
            context.setVariables(templateModel);
            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(SOURCE_EMAIL);
            helper.setTo("felipewai.dev@gmail.com");
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

            System.out.println("Email enviado com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao enviar email");
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }

    public void sendHtmlEmail(String to, String subject, Map<String, Object> templateModel) {
        sendHtmlEmail(to, subject, "resetPasswordEmail", templateModel);
    }
}