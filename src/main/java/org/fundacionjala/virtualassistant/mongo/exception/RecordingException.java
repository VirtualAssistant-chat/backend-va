package org.fundacionjala.virtualassistant.mongo.exception;

public class RecordingException extends Exception {

  public static final String MESSAGE_RECORDING_NULL = "The object Recording is null";
  public static final String MESSAGE_RECORDING_REQUEST_NULL = "The RecordingRequest is null";
  public static final String MESSAGE_NOT_WAV = "The provided file is not a valid .wav file";
  public static final String MESSAGE_NULL_AUDIO_FILE = "The audio file is null";

  public RecordingException(String message) {
    super(message);
  }

  public RecordingException(String message, Throwable cause) {
    super(message, cause);
  }
}
