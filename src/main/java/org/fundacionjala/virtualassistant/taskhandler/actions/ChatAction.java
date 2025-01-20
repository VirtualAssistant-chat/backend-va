package org.fundacionjala.virtualassistant.taskhandler.actions;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ChatAction implements TaskAction {

    private ChatService chatService;

    @Override
    public String handleAction(EntityArgs intentEntities, String text) {
        return chatService.chat(text);
    }
}
