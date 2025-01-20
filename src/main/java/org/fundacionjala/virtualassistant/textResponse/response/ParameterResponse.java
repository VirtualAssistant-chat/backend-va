package org.fundacionjala.virtualassistant.textResponse.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
@Jacksonized
public class ParameterResponse {
    @Valid
    TextRequestResponse request;

    @NotEmpty
    String text;
}
