package org.fundacionjala.virtualassistant.taskhandler.exception;

public class ConverterException extends Exception {
    public static final String CONVERTER_CLASS = "Converter Class";

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
