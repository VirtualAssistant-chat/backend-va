package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;

public interface TaskAction {
    String handleAction(EntityArgs intentEntities, String text);
}
