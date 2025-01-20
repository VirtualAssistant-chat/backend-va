package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

public interface TaskActionManagerFactory {
    TaskActionFactory getTaskActionFactory(String type) throws IntentException;
    Intent getIntent(String type) throws IntentException;
    void setIntentType(String intentType);
}
