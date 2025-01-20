package org.fundacionjala.virtualassistant.textResponse.exception;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;

public class TextResponseParserException extends ParserException {
    public static final String MESSAGE_RESPONSE_ENTITY = "Response entity must not be null";
    public static final String MESSAGE_PARAMETER_RESPONSE = "Parameter response must not be null";
    public static final String MESSAGE_TEXT_REQUEST = "Text Request must not be null";

    public TextResponseParserException(String message) {
        super(message);
    }

    public TextResponseParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
