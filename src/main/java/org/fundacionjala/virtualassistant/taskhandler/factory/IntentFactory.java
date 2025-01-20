package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;

public interface IntentFactory {
    IntentManager getSpecific(Intent intent) throws IntentException;
}
