package org.fundacionjala.virtualassistant.user_intetions.client;

import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.fundacionjala.virtualassistant.user_intetions.repository.UserIntentsClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Component
public class RasaClient implements UserIntentsClient {

    private static final String REQUEST_PAYLOAD_TEMPLATE = "{\"text\": \"%s\"}";

    private String rasaUrl;
    private RestTemplate restTemplate;

    public RasaClient(@Value("${rasa.url}") String rasaUrl) {
        this.rasaUrl = rasaUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<IntentResponse> processUserIntentsByMicroService(String input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestPayload = String.format(REQUEST_PAYLOAD_TEMPLATE, input);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);

        return restTemplate.exchange(
                rasaUrl,
                HttpMethod.POST,
                requestEntity,
                IntentResponse.class
        );
    }
}
