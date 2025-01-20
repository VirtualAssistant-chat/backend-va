package org.fundacionjala.virtualassistant.context.exception;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;

public class ContextParserException extends ParserException {
    public static final String MESSAGE_CONTEXT_REQUEST = "Context request must not be null";
    public static final String MESSAGE_CONTEXT_RESPONSE = "Context response must not be null";
    public static final String MESSAGE_CONTEXT_ENTITY = "Context entity must not be null";

    public ContextParserException(String message) {
        super(message);
    }

    public ContextParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
