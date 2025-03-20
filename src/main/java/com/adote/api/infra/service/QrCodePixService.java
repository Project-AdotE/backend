package com.adote.api.infra.service;

import com.adote.api.infra.dtos.qrCodePix.request.QrCodeRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.response.QrCodeResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodePixService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL = "https://www.gerarpix.com.br";

    public ResponseEntity<QrCodeResponseDTO> gerarQrCode(QrCodeRequestDTO qrCodeRequestDTO) {
        WebDriver driver = null;
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("key_type", qrCodeRequestDTO.tipo());
            requestBody.put("key", qrCodeRequestDTO.chave());
            requestBody.put("name", qrCodeRequestDTO.nome());
            requestBody.put("city", qrCodeRequestDTO.cidade());

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            System.out.println("Request URL: " + URL);
            System.out.println("Request Body: " + jsonBody);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            driver.get(URL);

            Thread.sleep(10000);

            String script = String.format(
                    "return fetch('%s/emvqr-static', {" +
                            "  method: 'POST'," +
                            "  headers: {" +
                            "    'Content-Type': 'application/json'," +
                            "    'Accept': 'application/json'" +
                            "  }," +
                            "  body: JSON.stringify(%s)" +
                            "}).then(response => {" +
                            "  return {" +
                            "    status: response.status," +
                            "    body: response.text()" +
                            "  };" +
                            "});", URL, jsonBody);

            Map<String, Object> jsResponse = (Map<String, Object>) ((JavascriptExecutor) driver).executeAsyncScript(
                    "const callback = arguments[arguments.length - 1];" +
                            "const doFetch = async () => {" +
                            "  try {" +
                            "    const response = await fetch('" + URL + "/emvqr-static', {" +
                            "      method: 'POST'," +
                            "      headers: {" +
                            "        'Content-Type': 'application/json'," +
                            "        'Accept': 'application/json'" +
                            "      }," +
                            "      body: '" + jsonBody.replace("'", "\\'") + "'" +
                            "    });" +
                            "    const status = response.status;" +
                            "    const text = await response.text();" +
                            "    callback({status: status, body: text});" +
                            "  } catch (error) {" +
                            "    callback({status: 500, body: error.toString()});" +
                            "  }" +
                            "};" +
                            "doFetch();"
            );

            int status = ((Long) jsResponse.get("status")).intValue();
            String responseBody = (String) jsResponse.get("body");

            // Log para debug
            System.out.println("Response Status: " + status);
            System.out.println("Response Body: " + responseBody);

            if (status == 200 && responseBody != null) {
                QrCodeResponseDTO responseDTO = objectMapper.readValue(responseBody, QrCodeResponseDTO.class);
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.status(status).body(null);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected Error: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            if (driver != null) {
                driver.quit();
            }
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
