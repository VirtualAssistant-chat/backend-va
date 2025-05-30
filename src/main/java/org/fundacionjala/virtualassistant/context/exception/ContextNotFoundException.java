package org.fundacionjala.virtualassistant.context.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContextNotFoundException extends ContextException {
    public ContextNotFoundException(String message) {
        super(message);
    }

    public ContextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
