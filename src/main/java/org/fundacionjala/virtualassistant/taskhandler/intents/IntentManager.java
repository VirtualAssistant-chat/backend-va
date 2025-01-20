package org.fundacionjala.virtualassistant.taskhandler.intents;

public interface IntentManager {
    <T extends Enum<?>> T processIntent(String type);
}
