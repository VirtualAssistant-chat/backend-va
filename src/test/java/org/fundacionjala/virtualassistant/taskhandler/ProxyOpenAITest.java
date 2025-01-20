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

class ProxyOpenAITest {
    private Proxy proxy;
    private RasaClient rasaClient;

    private static final String CHAT = "chat";
    private static final String RESULT = "result";
    private static final String ENTITY = "entity";
    private static final String VALUE = "value";
    private static final int ZERO = 0;
    private List<IntentEntity> entities = List.of(new IntentEntity(ENTITY, VALUE));
    private IntentResponse intentResponse = new IntentResponse(entities, new Intent(ZERO, CHAT));

    @BeforeEach
    void setUp() {
        rasaClient = mock(RasaClient.class);
        MusicService musicService = mock(MusicService.class);
        ChatService chatService = mock(ChatService.class);
        TaskActionManagerFactory taskActionManagerFactory = new TaskActionManagerFactoryImpl(musicService, chatService);
        IntentFactory intentFactory = new IntentFactoryImpl();

        proxy = new Proxy(rasaClient, intentFactory, taskActionManagerFactory);

        when(chatService.chat(any())).thenReturn(RESULT);
    }

    @Test
    void givenChatEnumWhenHandleIntentThenHandleIntent() throws IntentException {
        when(rasaClient.processUserIntentsByMicroService(any())).thenReturn(new ResponseEntity<>(intentResponse, HttpStatus.OK));

        String handledIntent = proxy.handleIntent(CHAT);
        assertNotNull(handledIntent);
        assertEquals(RESULT, handledIntent);
    }
}