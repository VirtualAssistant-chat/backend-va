package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactoryImpl;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactoryImpl;
import org.fundacionjala.virtualassistant.user_intetions.client.RasaClient;
import org.fundacionjala.virtualassistant.user_intetions.client.response.Intent;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProxySpotifyTest {
    private Proxy proxy;
    private RasaClient rasaClient;

    private static final String RESULT = "result";
    private static final String MUSIC_CONTINUE = "CONTINUE";
    private static final String MUSIC_PAUSE = "PAUSE";
    private static final String MUSIC_NEXT = "NEXT";
    private static final String MUSIC_ALBUMS = "GET_ALBUMS";
    private static final String MUSIC_FOLLOWING = "GET_FOLLOWING";
    private static final String MUSIC_PLAYER = "GET_PLAYER";
    private static final String MUSIC_PREVIOUS = "PREVIOUS";
    private static final String MUSIC_TRACKS = "GET_TRACKS";
    private static final String MUSIC_PLAY = "music_request";
    private static final String ENTITY = "entity";
    private static final String VALUE = "value";

    private List<IntentEntity> entities = List.of(new IntentEntity(ENTITY, VALUE),
            new IntentEntity(ENTITY, VALUE));

    @BeforeEach
    void setUp() {
        rasaClient = mock(RasaClient.class);
        MusicService musicService = mock(MusicService.class);
        ChatService chatService = mock(ChatService.class);
        TaskActionManagerFactory taskActionManagerFactory = new TaskActionManagerFactoryImpl(musicService, chatService);
        IntentFactory intentFactory = new IntentFactoryImpl();

        proxy = new Proxy(rasaClient, intentFactory, taskActionManagerFactory);

        when(musicService.getUserSavedAlbums()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.getUserSavedTracks()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.getUserFollowingArtists()).thenReturn(ResponseEntity.ok(RESULT));

        when(musicService.pauseCurrentTrack()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.playCurrentTrack()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.playNextTrack()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.playPreviousTrack()).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.playSongByArtistAndTrack(any(), any())).thenReturn(ResponseEntity.ok(RESULT));
        when(musicService.getUserPlayerInformation()).thenReturn(ResponseEntity.ok(RESULT));
    }

    @Test
    void givenGetAlbumEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_ALBUMS));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_ALBUMS);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenGetTracksEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_TRACKS));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_TRACKS);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenGetFollowingEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_FOLLOWING));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_FOLLOWING);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenGetPlayerEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_PLAYER));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_PLAYER);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenPauseEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_PAUSE));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_PAUSE);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenNextEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_NEXT));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_NEXT);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenPreviousEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_PREVIOUS));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_PREVIOUS);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenContinueEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_CONTINUE));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_CONTINUE);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }

    @Test
    void givenPlayEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        IntentResponse intentResponse = new IntentResponse(entities, new Intent(0, MUSIC_PLAY));
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse,
                HttpStatus.OK));

        String handledIntent = proxy.handleIntent(MUSIC_PLAY);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }
}
