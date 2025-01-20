package org.fundacionjala.virtualassistant.mongo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class RecordingRequest {
    @NotNull(message = "Id user must not be null")
    Long idUser;
    @NotNull(message = "Id chat must not be null")
    Long idChat;
    @NotNull(message = "Audio file must not be null")
    MultipartFile audioFile;
}