package com.adote.api.infra.service;

import com.adote.api.infra.dtos.qrCodePix.request.QrCodeExternalRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.request.QrCodeRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.response.QrCodeResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class QrCodePixService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://www.gerarpix.com.br/emvqr-static";

    public ResponseEntity<QrCodeResponseDTO> gerarQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
        try {
            QrCodeExternalRequestDTO objectRequest = new QrCodeExternalRequestDTO(
                    qrCodeRequestDTO.tipo(),
                    qrCodeRequestDTO.chave(),
                    qrCodeRequestDTO.nome(),
                    qrCodeRequestDTO.cidade(),
                    "",
                    ""
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            HttpEntity<QrCodeExternalRequestDTO> requestEntity = new HttpEntity<>(objectRequest, headers);

            ResponseEntity<QrCodeResponseDTO> response = restTemplate.postForEntity(URL, requestEntity, QrCodeResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.status(response.getStatusCode()).body(
                        new QrCodeResponseDTO(response.getBody().qrCode()
                        ));
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }

        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(null);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
