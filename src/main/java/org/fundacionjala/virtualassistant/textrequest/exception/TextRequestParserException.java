package org.fundacionjala.virtualassistant.textrequest.exception;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;

public class TextRequestParserException extends ParserException {
    public static final String MESSAGE_TEXT_REQUEST = "Text request must not be null";
    public static final String MESSAGE_TEXT_REQUEST_ENTITY = "Text request entity must not be null";

    public TextRequestParserException(String message) {
        super(message);
    }

    public TextRequestParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
