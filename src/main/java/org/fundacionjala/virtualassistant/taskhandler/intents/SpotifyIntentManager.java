package org.fundacionjala.virtualassistant.taskhandler.intents;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SpotifyIntentManager implements IntentManager {

    private Map<String, SpotifyIntent> spotifyIntentMap;

    public SpotifyIntentManager() {
        spotifyIntentMap = Map.of(
                "CONTINUE", SpotifyIntent.CONTINUE,
                "PAUSE", SpotifyIntent.PAUSE,
                "NEXT", SpotifyIntent.NEXT,
                "GET_ALBUMS", SpotifyIntent.GET_ALBUMS,
                "GET_FOLLOWING", SpotifyIntent.GET_FOLLOWING,
                "GET_PLAYER", SpotifyIntent.GET_PLAYER,
                "PREVIOUS", SpotifyIntent.PREVIOUS,
                "GET_TRACKS", SpotifyIntent.GET_TRACKS,
                "music_request", SpotifyIntent.music_request
        );
    }

    @Override
    public <T extends Enum<?>> T processIntent(String type) {
        return (T) spotifyIntentMap.get(type);
    }
}
