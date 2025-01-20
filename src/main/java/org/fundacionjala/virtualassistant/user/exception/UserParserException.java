package org.fundacionjala.virtualassistant.user.exception;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;

public class UserParserException extends ParserException {
    public static final String MESSAGE_USER_PARSER = "Error in User Parser";
    public static final String MESSAGE_USER_ENTITY = "User entity must not be null";
    public static final String MESSAGE_USER_REQUEST = "User request must not be null";

    public UserParserException(String message) {
        super(message);
    }

    public UserParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
