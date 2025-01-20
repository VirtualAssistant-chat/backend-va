package org.fundacionjala.virtualassistant.mongo.exception;

public class ConvertedDocumentToFileException extends RecordingException {
  public ConvertedDocumentToFileException(String message) {
    super(message);
  }

  public ConvertedDocumentToFileException(String message, Throwable cause) {
    super(message, cause);
  }
}
