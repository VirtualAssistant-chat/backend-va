package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;
import org.fundacionjala.virtualassistant.taskhandler.intents.OpenAIntentManager;
import org.fundacionjala.virtualassistant.taskhandler.intents.SpotifyIntentManager;
import org.springframework.stereotype.Component;

@Component
public class IntentFactoryImpl implements IntentFactory {
    @Override
    public IntentManager getSpecific(Intent intent) throws IntentException {
        switch (intent) {
            case SPOTIFY:
                return new SpotifyIntentManager();
            case CHAT_GPT:
                return new OpenAIntentManager();
            default:
                throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
    }
}
