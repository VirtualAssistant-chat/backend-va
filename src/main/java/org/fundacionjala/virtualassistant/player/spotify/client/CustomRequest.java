package org.fundacionjala.virtualassistant.player.spotify.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CustomRequest {

    private final static String AUTHORIZATION = "Authorization";
    private final static String BEARER = "Bearer ";

    private final RestTemplate restTemplate;

    public CustomRequest() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> exchange(String accessToken, String url, HttpMethod verb){
        HttpEntity<String> entity = new HttpEntity<>(createHeader(accessToken));
        return exchange(entity, url, verb);
    }

    public ResponseEntity<String> exchange(HttpHeaders headers, String url, HttpMethod verb){
        return exchange(new HttpEntity<>(headers), url, verb);
    }

    public ResponseEntity<String> exchange(HttpEntity<String> entity, String url, HttpMethod verb){
        return restTemplate.exchange(
                url,
                verb,
                entity,
                String.class
        );
    }

    public HttpHeaders createHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + accessToken);
        return headers;
    }
}
