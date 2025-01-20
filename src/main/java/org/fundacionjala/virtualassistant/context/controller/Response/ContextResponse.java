package org.fundacionjala.virtualassistant.context.controller.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;

import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class ContextResponse {
    @NotNull
    Long idContext;
    String title;
    Long idUser;

    public static ContextResponse fromEntity(ContextEntity context)
            throws ContextException {
        if (isNull(context)) {
            throw new ContextException(ContextException.MESSAGE_CONTEXT_NULL);
        }
        return ContextResponse.builder()
                .idUser(context.getUserEntity().getIdUser())
                .title(context.getTitle())
                .idContext(context.getIdContext())
                .build();
    }
}