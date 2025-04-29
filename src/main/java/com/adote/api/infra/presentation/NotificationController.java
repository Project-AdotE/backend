package com.adote.api.infra.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyOrganization(Long organizationId, String nomeAnimal) {
        String destination = "/topic/ong/" + organizationId + "/formulario";
        messagingTemplate.convertAndSend(destination, "Formulário novo recebido para animal: " + nomeAnimal);
    }
}
