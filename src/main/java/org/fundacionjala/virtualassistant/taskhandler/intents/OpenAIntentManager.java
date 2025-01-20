package org.fundacionjala.virtualassistant.taskhandler.intents;

import lombok.AllArgsConstructor;
import java.util.Map;

@AllArgsConstructor
public class OpenAIntentManager implements IntentManager {

    private Map<String, OpenAIntent> openAIntentMap;

    public OpenAIntentManager() {
        openAIntentMap = Map.of(
                "chat", OpenAIntent.CHAT
        );
    }

    @Override
    public <T extends Enum<?>> T processIntent(String type) {
        return (T) openAIntentMap.get(type);
    }
}
