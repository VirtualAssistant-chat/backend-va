package org.fundacionjala.virtualassistant.player.spotify.utils;

public enum ApiMusic {
    SAVED_ALBUMS("me/albums"),
    SAVED_TRACKS("me/tracks"),
    FOLLOWED("me/following?type=artist"),
    PLAYER_INFO("me/player"),
    PLAY("me/player/play"),
    PLAY_NEXT_TRACK("me/player/next"),
    PLAY_PREVIOUS_TRACK("me/player/previous"),
    PAUSE("me/player/pause"),
    SEARCH("search"),
    REDIRECT_AUTHORIZATION_PAGE("https://accounts.spotify.com/authorize"),
    TOKEN("https://accounts.spotify.com/api/token");

    private static final String BASE_URL = "https://api.spotify.com/v1/";
    private final String path;

    ApiMusic(String route) {
        this.path = route;
    }

    public StringBuilder getBuilder(){
        StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append(path);
        return sb;
    }

    public StringBuilder getPureBuilder(){
        return new StringBuilder(path);
    }

    public String url(){
        return getBuilder().toString();
    }

    public String pureUrl(){
        return path;
    }
}
