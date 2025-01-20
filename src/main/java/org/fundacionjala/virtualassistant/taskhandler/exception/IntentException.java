package org.fundacionjala.virtualassistant.taskhandler.exception;

public class IntentException extends Exception {
    public static final String INTENT_NOT_FOUND = "Intent not found";

    public IntentException(String message) {
        super(message);
    }

    public IntentException(String message, Throwable cause) {
        super(message, cause);
    }
}
