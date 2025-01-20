package org.fundacionjala.virtualassistant.context.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContextRequestException extends ContextException{
    public static final String MESSAGE_CONTEXT_ID_USER_DONT_EXIST = "The Id User not found in data base";
    public static final String MESSAGE_INVALID_TITLE = "The title in the context are invalid";
    public static final String MESSAGE_INVALID_ID = "The id in the context are invalid";

    public ContextRequestException(String message) {
        super(message);
    }

    public ContextRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}