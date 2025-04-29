package com.adote.api.infra.presentation;

import com.adote.api.infra.dtos.formulario.response.FormularioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyOrganization(String organizationId, FormularioResponseDTO formulario) {
        String destination = "/topic/ong/" + organizationId + "/formulario";
        messagingTemplate.convertAndSend(destination, formulario);
    }
}
