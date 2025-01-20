package org.fundacionjala.virtualassistant.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;

import java.util.List;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class UserContextResponse {
    Long idUser;
    String idGoogle;
    List<ContextResponse> contextResponses;
    String spotifyToken;
}
