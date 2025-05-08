package com.adote.api.infra.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Test
    void deveEnviarMensagemParaOngCorreta() {
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
        NotificationController controller = new NotificationController(messagingTemplate);

        Long organizationId = 123L;
        String nomeAnimal = "Animal";
        String expectedDestination = "/topic/ong/123/formulario";
        String expectedMessage = "Formulário novo recebido para animal: Animal";

        controller.notifyOrganization(organizationId, nomeAnimal);

        verify(messagingTemplate, times(1)).convertAndSend(expectedDestination, expectedMessage);
    }

    @Test
    void notifyOrganization() {
    }
}