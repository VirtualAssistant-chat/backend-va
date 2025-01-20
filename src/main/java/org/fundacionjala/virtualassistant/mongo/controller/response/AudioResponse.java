package org.fundacionjala.virtualassistant.mongo.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class AudioResponse {
    String nameAudio;
    @JsonIgnore
    byte[] audioByte;
}