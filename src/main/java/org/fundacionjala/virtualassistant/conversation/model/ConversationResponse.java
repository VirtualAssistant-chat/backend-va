package org.fundacionjala.virtualassistant.conversation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import java.time.ZonedDateTime;

@Value
@Builder
public class ConversationResponse {
    Long idRequest;
    String textRequest;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime dateRequest;
    String idAudio;
    String textResponse;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime dateResponse;
}
