package org.fundacionjala.virtualassistant.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class UserResponse {
    Long idUser;
    String idGoogle;
    String spotifyToken;
}
