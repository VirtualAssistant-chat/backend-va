package org.fundacionjala.virtualassistant.clients.openai.client;

import com.theokanning.openai.completion.CompletionRequest;

public interface AiClient {
    String getToken();

    CompletionRequest buildCompletionRequest(String request);
}
