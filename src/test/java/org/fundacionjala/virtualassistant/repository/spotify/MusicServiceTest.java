package org.fundacionjala.virtualassistant.repository.spotify;

import org.fundacionjala.virtualassistant.player.spotify.client.SpotifyClient;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

public class MusicServiceTest {

    public final static String TOKEN_NOT_AVAILABLE = "Access token not available.";
    public final static String PLAYING_NEXT_TRACK = "Playing next track.";
    public final static String PLAYING_PREVIOUS_TRACK = "Playing previous track.";
    public final static String PLAYBACK_RESUMED = "Playback has been resumed.";
    public final static String CURRENT_TRACK_PAUSED = "Current track has been paused.";
    public final static String NO_TRACK_PLAYING = "No track is currently playing.";
    public final static String FOLLOWING_DATA = "Radiohead, Gustavo Cerati, Arctic Monkeys";
    public final static String ACCESS_TOKEN = "Access Token";
    public final static String PLAYER_DATA = "Player Data";
    public final static String ARTIST_NAME = "Artist Name";
    public final static String TRACK_NAME = "Track Name";
    public final static String CURRENT_TRACK_NAME = "Current Track Name";

    @Test
    public void testGetUserSavedAlbums_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        String realAlbumData = "{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}";
        when(spotifyClient.getSavedAlbums()).thenReturn(realAlbumData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"items\": [{\"album\": {\"name\": \"Album 1\"}}, {\"album\": {\"name\": \"Album 2\"}}]}", response.getBody());
    }

    @Test
    public void testGetUserSavedAlbums_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedAlbums();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testGetUserSavedTracks_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        String realTracksData = "{\"items\": [{\"track\": {\"name\": \"Track 1\"}}, {\"track\": {\"name\": \"Track 2\"}}]}";
        when(spotifyClient.getSavedTracks()).thenReturn(realTracksData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedTracks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(realTracksData, response.getBody());
    }

    @Test
    public void testGetUserSavedTracks_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserSavedTracks();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testPlayPreviousTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.playPreviousTrackOnDevice()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playPreviousTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PLAYING_PREVIOUS_TRACK, response.getBody());
    }

    @Test
    public void testPlayPreviousTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playPreviousTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testPlayCurrentTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.playCurrentSong()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playCurrentTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PLAYBACK_RESUMED, response.getBody());

        verify(spotifyClient, times(1)).playCurrentSong();
    }

    @Test
    public void testPlayCurrentTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playCurrentTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());

        verify(spotifyClient, never()).playCurrentSong();
    }

    @Test
    public void testGetUserSavedFollowing_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        when(spotifyClient.getFollowed()).thenReturn(FOLLOWING_DATA);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserFollowingArtists();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(FOLLOWING_DATA, response.getBody());
    }

    @Test
    public void testGetUserSavedFollowing_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserFollowingArtists();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testGetUserPlayerInformation_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        String realPlayerData = "{\"device\": {\"name\": \"Device 1\"}, \"is_playing\": true, \"item\": {\"name\": \"Song 1\"}}";
        when(spotifyClient.getPlayerInfo()).thenReturn(realPlayerData);

        String simplifiedPlayerData = "{\"device\": \"Device 1\", \"is_playing\": true, \"current_track\": \"Song 1\"}";
        when(spotifyClient.extractPlayerData(realPlayerData)).thenReturn(simplifiedPlayerData);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserPlayerInformation();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(simplifiedPlayerData, response.getBody());
    }

    @Test
    public void testGetUserPlayerInformation_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.getUserPlayerInformation();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testPauseCurrentTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.getPlayerInfo()).thenReturn(PLAYER_DATA);
        when(spotifyClient.extractCurrentTrackUri(PLAYER_DATA)).thenReturn(CURRENT_TRACK_NAME);
        when(spotifyClient.pauseSongOnDevice(any())).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.pauseCurrentTrack();
        assertEquals(CURRENT_TRACK_PAUSED, response.getBody());

        verify(spotifyClient, times(1)).pauseSongOnDevice(CURRENT_TRACK_NAME);
    }

    @Test
    public void testPauseCurrentTrack_NoTrackPlaying() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.getPlayerInfo()).thenReturn(PLAYER_DATA);
        when(spotifyClient.extractCurrentTrackUri(PLAYER_DATA)).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.pauseCurrentTrack();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(NO_TRACK_PLAYING, response.getBody());

        verify(spotifyClient, never()).pauseSongOnDevice(any());
    }

    @Test
    public void testPlayNextTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.playNextTrackOnDevice()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playNextTrack();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PLAYING_NEXT_TRACK, response.getBody());
    }

    @Test
    public void testPlayNextTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playNextTrack();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());
    }

    @Test
    public void testPlaySongByArtistAndTrack_Success() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);

        String artist = ARTIST_NAME;
        String track = TRACK_NAME;
        String trackUri = "spotify:track:track_id";
        when(spotifyClient.searchTrackByArtistAndTrack(artist, track)).thenReturn(trackUri);

        when(spotifyClient.playSongOnDevice(trackUri)).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playSongByArtistAndTrack(artist, track);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Playing song by Artist Name: Track Name", response.getBody());

        verify(spotifyClient, times(1)).searchTrackByArtistAndTrack(artist, track);
        verify(spotifyClient, times(1)).playSongOnDevice(trackUri);
    }

    @Test
    public void testPlaySongByArtistAndTrack_Unauthorized() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.isNotAuthorized()).thenReturn(true);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playSongByArtistAndTrack(ARTIST_NAME, TRACK_NAME);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(TOKEN_NOT_AVAILABLE, response.getBody());

        verify(spotifyClient, never()).searchTrackByArtistAndTrack(any(), any());
        verify(spotifyClient, never()).playSongOnDevice(any());
    }

    @Test
    public void testPlaySongByArtistAndTrack_TrackNotFound() {
        SpotifyClient spotifyClient = mock(SpotifyClient.class);
        when(spotifyClient.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(spotifyClient.playSongOnDevice(any())).thenReturn(false);

        String artist = "Non-existent Artist";
        String track = "Non-existent Track";
        when(spotifyClient.searchTrackByArtistAndTrack(artist, track)).thenReturn(null);

        MusicService musicService = new MusicService(spotifyClient);

        ResponseEntity<String> response = musicService.playSongByArtistAndTrack(artist, track);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Song not found.", response.getBody());

        verify(spotifyClient, never()).playSongOnDevice(any());
    }
}
