package org.fundacionjala.virtualassistant.taskhandler.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.actions.ChatAction;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;

@AllArgsConstructor
@NoArgsConstructor
public class OpenAITaskActionFactory implements TaskActionFactory {

    private ChatService chatService;

    @Override
    public <T extends Enum<?>> TaskAction createTaskAction(T intent) throws IntentException {
        try {
            return new ChatAction(chatService);
        }
        catch (Exception e) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
    }
}
