package com.adote.api.infra.service;

import com.adote.api.infra.dtos.qrCodePix.request.QrCodeRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.response.QrCodeResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodePixService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL = "https://www.gerarpix.com.br/emvqr-static";

    public ResponseEntity<QrCodeResponseDTO> gerarQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("key_type", qrCodeRequestDTO.tipo());
            requestBody.put("key", qrCodeRequestDTO.chave());
            requestBody.put("name", qrCodeRequestDTO.nome());
            requestBody.put("city", qrCodeRequestDTO.cidade());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            System.out.println("Request URL: " + URL);
            System.out.println("Request Headers: " + headers);
            System.out.println("Request Body: " + objectMapper.writeValueAsString(requestBody));

            ResponseEntity<String> rawResponse = restTemplate.postForEntity(URL, requestEntity, String.class);

            System.out.println("Response Status: " + rawResponse.getStatusCode());
            System.out.println("Response Headers: " + rawResponse.getHeaders());
            System.out.println("Response Body: " + rawResponse.getBody());

            if (rawResponse.getStatusCode().is2xxSuccessful() && rawResponse.getBody() != null) {
                QrCodeResponseDTO responseDTO = objectMapper.readValue(rawResponse.getBody(), QrCodeResponseDTO.class);
                return ResponseEntity.status(rawResponse.getStatusCode()).body(responseDTO);
            } else {
                return ResponseEntity.status(rawResponse.getStatusCode()).body(null);
            }

        } catch (HttpStatusCodeException ex) {
            System.out.println("Error Status: " + ex.getStatusCode());
            System.out.println("Error Response: " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(null);

        } catch (Exception ex) {
            System.out.println("Unexpected Error: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

//@Service
//public class QrCodePixService {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private static final String URL = "https://www.gerarpix.com.br/emvqr-static";
//
//    public ResponseEntity<QrCodeResponseDTO> gerarQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
//        try {
//            QrCodeExternalRequestDTO objectRequest = new QrCodeExternalRequestDTO(
//                    qrCodeRequestDTO.tipo(),
//                    qrCodeRequestDTO.chave(),
//                    qrCodeRequestDTO.nome(),
//                    qrCodeRequestDTO.cidade(),
//                    "",
//                    ""
//            );
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
//            HttpEntity<QrCodeExternalRequestDTO> requestEntity = new HttpEntity<>(objectRequest, headers);
//
//            ResponseEntity<QrCodeResponseDTO> response = restTemplate.postForEntity(URL, requestEntity, QrCodeResponseDTO.class);
//
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//                return ResponseEntity.status(response.getStatusCode()).body(
//                        new QrCodeResponseDTO(response.getBody().qrCode()
//                        ));
//            } else {
//                return ResponseEntity.status(response.getStatusCode()).body(null);
//            }
//
//        } catch (HttpStatusCodeException ex) {
//            return ResponseEntity.status(ex.getStatusCode()).body(null);
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//}
