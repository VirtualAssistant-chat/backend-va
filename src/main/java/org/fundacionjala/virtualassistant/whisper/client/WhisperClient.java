package org.fundacionjala.virtualassistant.whisper.client;

import lombok.Data;

import java.io.IOException;

import org.fundacionjala.virtualassistant.whisper.repository.ASRClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Component
public class WhisperClient implements ASRClient {

    @Value("${asr.whisper.url}")
    private String url;

    @Value("${asr.whisper.post-endpoint}")
    private String postEndpoint;

    @Override
    public String convertToText(MultipartFile audioFile) throws IOException {
        byte[] audioData = audioFile.getBytes();

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();

        return webClient.post()
                .uri(postEndpoint)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(getBody(audioData, audioFile.getOriginalFilename())))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public MultiValueMap<String, HttpEntity<?>> getBody(byte[] audioData, String filename) {
        MultiValueMap<String, HttpEntity<?>> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(audioData) {
            @Override
            public String getFilename() {
                return filename;
            }
        };
        body.add("audio_file", new HttpEntity<>(resource));
        return body;
    }
}