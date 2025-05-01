package com.adote.api.infra.service;

import com.adote.api.infra.config.aws.secrets.mail.MailgunProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailgunProperties mailgunProperties;
    private final SpringTemplateEngine templateEngine;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.mailgun.net/v3/" + mailgunProperties.getDomain())
                .defaultHeaders(headers -> headers.setBasicAuth("api", mailgunProperties.getApiKey()))
                .build();
    }

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel) {
        try {
            Context context = new Context();
            context.setVariables(templateModel);
            String htmlContent = templateEngine.process(templateName, context);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("from", mailgunProperties.getFrom());
            formData.add("to", "felipewai.dev@gmail.com");
            formData.add("subject", subject);
            formData.add("html", htmlContent);

            webClient.post()
                    .uri("/messages")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnError(e -> System.out.println("Erro ao enviar e-mail: " + e.getMessage()))
                    .subscribe();

            System.out.println("Email enviado com sucesso");

        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }

    public void sendHtmlEmail(String to, String subject, Map<String, Object> templateModel) {
        sendHtmlEmail(to, subject, "resetPasswordEmail", templateModel);
    }
}