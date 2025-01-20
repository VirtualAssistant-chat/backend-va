package org.fundacionjala.virtualassistant.player.spotify.service;

import org.fundacionjala.virtualassistant.player.spotify.client.MusicClient;
import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.utils.CustomResponse;
import org.fundacionjala.virtualassistant.player.spotify.utils.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    private final MusicClient spotifyClient;

    @Autowired
    public MusicService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public ResponseEntity<String> getUserSavedAlbums() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String savedAlbums = spotifyClient.getSavedAlbums();
        return CustomResponse.success(savedAlbums);
    }

    public ResponseEntity<String> getUserSavedTracks() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String tracksData = spotifyClient.getSavedTracks();
        return CustomResponse.success(tracksData);
    }

    public ResponseEntity<String> getUserFollowingArtists() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String followingData = spotifyClient.getFollowed();
        return CustomResponse.success(followingData);
    }

    public ResponseEntity<String> getUserPlayerInformation() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String playerData = spotifyClient.getPlayerInfo();
        String simplifiedData = spotifyClient.extractPlayerData(playerData);
        return CustomResponse.success(simplifiedData);
    }

    public ResponseEntity<String> playCurrentTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        boolean isSuccessful = spotifyClient.playCurrentSong();
        return CustomResponse.eval(isSuccessful, TypeResponse.PLAYBACK_RESUMED);
    }


    public ResponseEntity<String> pauseCurrentTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String playerData = spotifyClient.getPlayerInfo();
        String currentTrackUri = spotifyClient.extractCurrentTrackUri(playerData);

        if (currentTrackUri == null) {
            return CustomResponse.badRequest();
        }

        boolean isSuccessful = spotifyClient.pauseSongOnDevice(currentTrackUri);
        return CustomResponse.eval(isSuccessful, TypeResponse.TRACK_PAUSED);
    }

    public boolean logTheUserOut() {
        return spotifyClient.logout();
    }

    public ResponseEntity<String> playNextTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        boolean isSuccessful = spotifyClient.playNextTrackOnDevice();
        return CustomResponse.eval(isSuccessful, TypeResponse.PLAY_NEXT);
    }

    public ResponseEntity<String> playPreviousTrack() {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        boolean isSuccessful = spotifyClient.playPreviousTrackOnDevice();
        return CustomResponse.eval(isSuccessful, TypeResponse.PLAY_PREVIOUS);
    }

    public ResponseEntity<String> playSongByArtistAndTrack(String artist, String track) {
        if (spotifyClient.isNotAuthorized()) {
            return CustomResponse.notAccessTokenResponse();
        }

        String trackUri = spotifyClient.searchTrackByArtistAndTrack(artist, track);

        if (trackUri == null) {
            return CustomResponse.songNotFound();
        }

        boolean isSuccessful = spotifyClient.playSongOnDevice(trackUri);
        if (isSuccessful) {
            return CustomResponse.successPlaySong(artist, track);
        } else {
            return CustomResponse.eval(false,TypeResponse.PLAY_SONG);
        }
    }
}
