package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class TaskActionManagerFactoryImpl implements TaskActionManagerFactory {
    private String intentType;
    private MusicService musicService;
    private ChatService chatService;

    public TaskActionManagerFactoryImpl(MusicService musicService, ChatService chatService) {
        this.musicService = musicService;
        this.chatService = chatService;
    }

    @Override
    public TaskActionFactory getTaskActionFactory(String type) throws IntentException {
        Intent intent = getIntent(type);
        if (Objects.isNull(intent)) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
        switch (intent) {
            case SPOTIFY:
                return new SpotifyTaskActionFactory(musicService);
            case CHAT_GPT:
                return new OpenAITaskActionFactory(chatService);
            default:
                throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
    }

    @Override
    public Intent getIntent(String type) {
        switch (intentType) {
            case "CONTINUE":
            case "PAUSE":
            case "NEXT":
            case "GET_ALBUMS":
            case "GET_FOLLOWING":
            case "GET_PLAYER":
            case "PREVIOUS":
            case "GET_TRACKS":
            case "music_request":
                return Intent.SPOTIFY;
            default:
                return Intent.CHAT_GPT;
        }
    }

    @Override
    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }
}
