package org.fundacionjala.virtualassistant.clients.openai.exeption;

import com.theokanning.openai.OpenAiError;
import com.theokanning.openai.OpenAiHttpException;

public class ChatServiceOpenAiHttpException extends OpenAiHttpException {

    public ChatServiceOpenAiHttpException(OpenAiError error, Exception parent, int statusCode) {
        super(error, parent, statusCode);
    }
    
}
