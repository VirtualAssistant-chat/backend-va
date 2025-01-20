package org.fundacionjala.virtualassistant.player.spotify.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.MusicPlayerException;
import org.fundacionjala.virtualassistant.player.spotify.exceptions.TokenExtractionException;
import java.io.IOException;

public class JsonNodeManagement {
    private final static String FIELD_CURRENT_PLAYING_TYPE =  "currently_playing_type";
    private final static String RESULT_CURRENT_PLAYING_TYPE =  "Playback Type: ";
    private final static String FIELD_ITEM = "item";
    private final static String FIELD_ARTISTS = "artists";
    private final static String FIELD_ALBUM = "album";
    private final static String FIELD_NAME = "name";
    private final static String FIELD_TRACKS = "tracks";
    private final static String FIELD_ITEMS = "items";
    private final static String RESULT_ARTISTS = "Artist: ";
    private final static String RESULT_ALBUM = "Album: ";
    private final static String RESULT_NAME = "Track: ";
    private final static String RESULT_MSG = "Currently playing: ";
    private final static String TRACK = "track";
    private final static String URI = "uri";
    private final static String NEW_LINE = "\n";
    private final static String DATA_PLAYER_PROCESSING = "Error processing player data";
    private final static String NO_TRACK_PLAYING = "No track is currently playing.";
    private final static String EMPTY_STRING = "";
    private final ObjectMapper objectMapper;

    public JsonNodeManagement (ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String extractPlayerData(String playerData) throws TokenExtractionException {
        StringBuilder result = new StringBuilder();

        try {
            JsonNode root = objectMapper.readTree(playerData);
            String playbackType = root.path(FIELD_CURRENT_PLAYING_TYPE).asText();
            result.append(RESULT_CURRENT_PLAYING_TYPE).append(playbackType).append(NEW_LINE);

            if (TRACK.equals(playbackType)) {
                JsonNode trackNode = root.path(FIELD_ITEM);
                if (trackNode.isMissingNode()) {
                    throw new MusicPlayerException(NO_TRACK_PLAYING);
                }

                String artistName = trackNode.path(FIELD_ARTISTS).get(0).path(FIELD_NAME).asText();
                String albumName = trackNode.path(FIELD_ALBUM).path(FIELD_NAME).asText();
                String trackName = trackNode.path(FIELD_NAME).asText();

                result.append(RESULT_ARTISTS).append(artistName).append(NEW_LINE);
                result.append(RESULT_ALBUM).append(albumName).append(NEW_LINE);
                result.append(RESULT_NAME).append(trackName).append(NEW_LINE);
            } else {
                result.append(RESULT_MSG).append(playbackType).append(NEW_LINE);
            }

        } catch (Exception e) {
            throw new TokenExtractionException(DATA_PLAYER_PROCESSING, e);
        }

        return result.toString();
    }

    public String extractTrackUriFromSearchResponse(String responseData) throws TokenExtractionException {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseData);
            JsonNode tracksNode = jsonNode.path(FIELD_TRACKS).path(FIELD_ITEMS);
            if (tracksNode.isArray() && tracksNode.size() > 0) {
                JsonNode firstItem = tracksNode.get(0);
                if (firstItem.has(URI)) {
                    return firstItem.get(URI).asText();
                }
            }
        } catch (Exception e) {
            throw new TokenExtractionException(DATA_PLAYER_PROCESSING, e);
        }
        return EMPTY_STRING;
    }

    public String extractCurrentTrackUri(String playerData) throws TokenExtractionException{
        try {
            JsonNode jsonNode = objectMapper.readTree(playerData);

            if (jsonNode.has(FIELD_ITEM)) {
                JsonNode item = jsonNode.get(FIELD_ITEM);
                if (item.has(URI)) {
                    return item.get(URI).asText();
                }
            }
        } catch (IOException e) {
            throw new TokenExtractionException(DATA_PLAYER_PROCESSING, e);
        }
        return EMPTY_STRING;
    }
}
