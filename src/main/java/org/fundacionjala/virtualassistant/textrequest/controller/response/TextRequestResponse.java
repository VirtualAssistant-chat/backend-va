package org.fundacionjala.virtualassistant.textrequest.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class TextRequestResponse {
  @NotNull
  Long idRequest;
  Long idUser;
  Long idContext;
  String text;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  ZonedDateTime date;
  TextResponse textResponse;
}
