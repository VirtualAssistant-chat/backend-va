package org.fundacionjala.virtualassistant.context.controller.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class ContextRequest {
    @NotEmpty(message = "title file must not be empty")
    @Pattern(regexp = ".*[a-zA-Z0-9].*")
    String title;
    @Valid
    UserRequest userRequest;
}