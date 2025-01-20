package org.fundacionjala.virtualassistant.player.spotify.utils;

public enum TypeResponse {
    PLAY_NEXT("Playing next track.", "Failed to play next track."),
    PLAY_PREVIOUS("Playing previous track.", "Failed to play previous track."),
    PLAY_SONG("Playing song by ", "Failed to play the song."),
    PLAYBACK_RESUMED("Playback has been resumed.", "Failed to resume playback."),
    TRACK_PAUSED("Current track has been paused.", "Failed to pause the current track.");

    final String successMessage;
    final String failedMessage;

    TypeResponse(String successMessage, String failedMessage) {
        this.successMessage = successMessage;
        this.failedMessage = failedMessage;
    }

    public String getFailedMessage() {
        return failedMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
