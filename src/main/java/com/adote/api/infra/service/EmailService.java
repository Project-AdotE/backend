package com.adote.api.infra.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;
    private final AmazonSimpleEmailService amazonSES;

    private final String SOURCE_EMAIL = "felipewai.dev@gmail.com";

    public SendEmailResult sendHtmlEmailWithResult(String to, String subject, String templateName, Map<String, Object> templateModel) {
        try {
            Context context = new Context();
            context.setVariables(templateModel);
            String htmlContent = templateEngine.process(templateName, context);

            Body body = new Body().withHtml(new Content()
                    .withCharset(StandardCharsets.UTF_8.name())
                    .withData(htmlContent));

            SendEmailRequest request = new SendEmailRequest()
                    .withSource(SOURCE_EMAIL)
                    .withDestination(new Destination().withToAddresses("felipe.losadawai0@gmail.com"))
                    .withMessage(new Message()
                            .withBody(body)
                            .withSubject(new Content()
                                    .withCharset(StandardCharsets.UTF_8.name())
                                    .withData(subject)));

            SendEmailResult result = amazonSES.sendEmail(request);
            System.out.println("Email enviado com sucesso");

            return result;
        } catch (Exception e) {
            System.out.println("Erro ao enviar email");
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel) {
        sendHtmlEmailWithResult(to, subject, templateName, templateModel);
    }

    public void sendHtmlEmail(String to, String subject, Map<String, Object> templateModel) {
        sendHtmlEmail(to, subject, "resetPasswordEmail", templateModel);
    }
}