package org.fundacionjala.virtualassistant.player.spotify.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {
    private final static String NOT_ACCESS_TOKEN = "Access token not available.";
    private final static String SUCCESS_LOGIN = "Logged in successfully.";
    private final static String SONG_NOT_FOUND = "Song not found.";
    private final static String BAD_REQUEST = "No track is currently playing.";
    private final static String COLON = ": ";

    public static ResponseEntity<String> notAccessTokenResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(NOT_ACCESS_TOKEN);
    }

    public static ResponseEntity<String> successLogin() {
        return success(SUCCESS_LOGIN);
    }

    public static ResponseEntity<String> failedLogin(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    public static ResponseEntity<String> songNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SONG_NOT_FOUND);
    }

    public static ResponseEntity<String> badRequest() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BAD_REQUEST);
    }


    public static ResponseEntity<String> success(String message) {
        return ResponseEntity.ok(message);
    }

    public static ResponseEntity<String> failed(String message) {
        return ResponseEntity.ok(message);
    }

    public static ResponseEntity<String> eval(Boolean isSuccessful, TypeResponse typeResponse) {
        if(isSuccessful) {
            return success(typeResponse.getSuccessMessage());
        } else {
            return failed(typeResponse.getFailedMessage());
        }
    }

    public static ResponseEntity<String> successPlaySong(String artist, String track) {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(TypeResponse.PLAY_SONG.getSuccessMessage())
                .append(artist).append(COLON).append(track);
        return CustomResponse.success(responseBuilder.toString());
    }
}
