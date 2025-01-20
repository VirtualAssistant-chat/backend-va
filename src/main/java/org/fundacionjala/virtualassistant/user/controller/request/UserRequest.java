package org.fundacionjala.virtualassistant.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotNull
    Long idUser;
    String idGoogle;
    String spotifyToken; 
}
