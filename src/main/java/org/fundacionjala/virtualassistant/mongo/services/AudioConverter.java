package org.fundacionjala.virtualassistant.mongo.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import org.fundacionjala.virtualassistant.mongo.controller.response.AudioResponse;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.validation.constraints.NotNull;

public class AudioConverter {

    private static final String CONTENT_DISPOSITION_PREFIX = "inline; filename=";
    public static ResponseEntity<InputStreamResource> convertRecordingToInputStreamResource(@NotNull Optional<RecordingResponse> recording) {
        if (recording.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AudioResponse audioResponse = recording.get().getAudioResponse();
        InputStream inputStream = new ByteArrayInputStream(audioResponse.getAudioByte());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, CONTENT_DISPOSITION_PREFIX + audioResponse.getNameAudio());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}