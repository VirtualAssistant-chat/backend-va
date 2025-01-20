package org.fundacionjala.virtualassistant.textResponse.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import lombok.Builder;
import lombok.AllArgsConstructor;
import java.time.ZonedDateTime;

@Value
@Builder
@AllArgsConstructor
public class TextResponse {
    Long idResponse;
    Long idRequest;
    String text;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime date;
}
