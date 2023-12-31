package com.mpesa_express.mpesa_express_java.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaExpressApiService {

    private final String consumerKey = "bPe1YNcyqJpcsTleqqDab4xpEoyiXC3k";
    private final String consumerSecret = "wkxkW79hN317odYW";
    private final String accessTokenUrl = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
    private final String storeNumber = "174379";
    private final String passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    private final String stkPushUrl = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
    private final String timestamp = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            .format(java.time.LocalDateTime.now());
    private final String password = storeNumber + passKey + timestamp;
    private final String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
    private String credentials = consumerKey + ":" + consumerSecret;
    private String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    @GetMapping("/generateAccessToken")

    public String generateAccessToken() {
        try {
            // Create headers with Authorization
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedCredentials);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Send request and handle the response
            ResponseEntity<String> response = restTemplate.exchange(
                    accessTokenUrl,
                    HttpMethod.GET,
                    createRequestEntity(),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Parse JSON response to get the access token
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = response.getBody();
                if (responseBody != null) {
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    String accessToken = jsonNode.get("access_token").asText();
                    return accessToken;
                } else {
                    // Log the warning
                    java.util.logging.Logger.getLogger("Response body is null.");
                    return null;
                }
            } else {
                // Log the error
                java.util.logging.Logger
                        .getLogger("Failed to get access token. Status code: " + response.getStatusCode());
                throw new AccessTokenException("Failed to get access token.", null);
            }
        } catch (Exception e) {
            // Log the exception
            java.util.logging.Logger.getLogger("An error occurred while getting access token: " + e.getMessage());
            throw new AccessTokenException("An error occurred while getting access token.", e);
        }
    }

    @PostMapping("/stkPush")
    public String stkPush(@RequestParam String phoneNumber, @RequestParam String amount) {
        try {
            String accessToken = generateAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("BusinessShortCode", storeNumber);
            requestBody.put("Password", encodedPassword);
            requestBody.put("Timestamp", timestamp);
            requestBody.put("TransactionType", "CustomerPayBillOnline");
            requestBody.put("Amount", amount);
            requestBody.put("PartyA", phoneNumber);
            requestBody.put("PartyB", storeNumber);
            requestBody.put("PhoneNumber", phoneNumber);
            requestBody.put("CallBackURL", "https://mpesa-requestbin.herokuapp.com/1fj5q1j1");
            requestBody.put("AccountReference", "Test");
            requestBody.put("TransactionDesc", "Test");

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(stkPushUrl, httpEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Parse JSON response to get the response code
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = responseEntity.getBody();
                if (responseBody != null) {
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    String responseCode = jsonNode.get("ResponseCode").asText();
                    return responseCode;
                } else {
                    // Log the warning
                    java.util.logging.Logger.getLogger("Response body is null.");
                    return null;
                }

            } else {
                // Log the error
                java.util.logging.Logger
                        .getLogger("Failed to push stk. Status code: " + responseEntity.getStatusCode());
                throw new StkPushException("Failed to push stk.", null);
            }
        } catch (Exception e) {
            // Log the exception
            java.util.logging.Logger.getLogger("An error occurred while pushing stk: " + e.getMessage());
            throw new StkPushException("An error occurred while pushing stk.", e);
        }
    }

    private HttpEntity<String> createRequestEntity() {

        // Create headers with Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity with headers
        return new HttpEntity<>(headers);
    }

}
