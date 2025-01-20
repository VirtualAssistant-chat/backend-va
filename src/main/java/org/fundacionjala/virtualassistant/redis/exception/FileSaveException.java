package org.fundacionjala.virtualassistant.redis.exception;

public class FileSaveException extends Exception {
    public static final String FAILED_MESSAGE = "Failed to save file in Redis";

    public FileSaveException(String message) {
        super(message);
    }

    public FileSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
