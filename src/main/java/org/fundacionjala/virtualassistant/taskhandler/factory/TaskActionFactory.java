package org.fundacionjala.virtualassistant.taskhandler.factory;

import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.springframework.stereotype.Component;

@Component
public interface TaskActionFactory {
    <T extends Enum<?>> TaskAction createTaskAction(T intent) throws IntentException;
}
