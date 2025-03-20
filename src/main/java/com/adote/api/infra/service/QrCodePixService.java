package com.adote.api.infra.service;

import com.adote.api.infra.dtos.qrCodePix.request.QrCodeRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.response.QrCodeResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodePixService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL = "https://www.gerarpix.com.br/emvqr-static";

    public ResponseEntity<QrCodeResponseDTO> gerarQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("key_type", qrCodeRequestDTO.tipo());
            requestBody.put("key", qrCodeRequestDTO.chave());
            requestBody.put("name", qrCodeRequestDTO.nome());
            requestBody.put("city", qrCodeRequestDTO.cidade());

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            System.out.println("Request URL: " + URL);
            System.out.println("Request Body: " + jsonBody);

            Connection connection = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.POST)
                    .requestBody(jsonBody)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Origin", "https://www.gerarpix.com.br")
                    .header("Referer", "https://www.gerarpix.com.br/")
                    .header("Sec-Ch-Ua", "\"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\", \"Not=A?Brand\";v=\"99\"")
                    .header("Sec-Ch-Ua-Mobile", "?0")
                    .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .timeout(30000);

            Connection.Response response = connection.execute();

            System.out.println("Response Status: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() == 200 && response.body() != null) {
                QrCodeResponseDTO responseDTO = objectMapper.readValue(response.body(), QrCodeResponseDTO.class);
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.status(response.statusCode()).body(null);
            }
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
