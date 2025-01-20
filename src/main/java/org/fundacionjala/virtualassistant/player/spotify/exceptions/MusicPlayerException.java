package org.fundacionjala.virtualassistant.player.spotify.exceptions;

public class MusicPlayerException extends RuntimeException {

    public MusicPlayerException(String message) {
        super(message);
    }

    public MusicPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
