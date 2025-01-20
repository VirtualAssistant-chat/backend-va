package org.fundacionjala.virtualassistant.player.spotify.client;

import org.fundacionjala.virtualassistant.player.spotify.utils.ApiMusic;

public class CustomQuery {
    private final static String ACCESS_CODE = "grant_type=authorization_code&code=";
    private final static String ACCESS_URI = "&redirect_uri=";
    private final static String ACCESS_SCOPE = "&scope=";
    private final static String SEARCH_QUERY_ARTIST = "?q=";
    private final static String SEARCH_TRACK_LIMIT = "&type=track&limit=1";
    private final static String AUTHORIZATION_QUERY_ID = "?client_id=";
    private final static String AUTHORIZATION_TYPE_URI = "&response_type=code&redirect_uri=";
    private final static String AUTHORIZATION_SCOPE = "&scope=user-read-private%20user-read-email user-library-read user-follow-read user-read-playback-state app-remote-control user-modify-playback-state";
    private final static String URI_START = "{\"uris\": [\"";
    private final static String URI_END = "\"]}";
    private final static String SPACE = " ";

    public static String authorizationRequestBody(String id, String uri) {
        StringBuilder spotifyAuthUrl = ApiMusic.REDIRECT_AUTHORIZATION_PAGE.getPureBuilder();
        spotifyAuthUrl.append(AUTHORIZATION_QUERY_ID).append(id)
                .append(AUTHORIZATION_TYPE_URI).append(uri)
                .append(AUTHORIZATION_SCOPE);
        return spotifyAuthUrl.toString();
    }
    public static String accessRequestBody(String code, String uri, String scope) {
        StringBuilder requestBody = new StringBuilder(ACCESS_CODE)
                .append(code)
                .append(ACCESS_URI).append(uri)
                .append(ACCESS_SCOPE).append(scope);
        return requestBody.toString();
    }

    public static String searchRequestBody(String artist, String track) {
        StringBuilder requestBody = ApiMusic.SEARCH.getBuilder()
                .append(SEARCH_QUERY_ARTIST).append(artist)
                .append(SPACE)
                .append(track)
                .append(SEARCH_TRACK_LIMIT);
        return requestBody.toString();
    }

    public static String uriRequestBody(String uri) {
        StringBuilder requestBody = new StringBuilder(URI_START)
                .append(uri)
                .append(URI_END);
        return requestBody.toString();
    }
}
