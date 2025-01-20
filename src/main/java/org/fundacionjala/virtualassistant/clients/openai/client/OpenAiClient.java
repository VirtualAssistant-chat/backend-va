package org.fundacionjala.virtualassistant.clients.openai.client;

import com.theokanning.openai.completion.CompletionRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:openAi.properties")
@NoArgsConstructor
public class OpenAiClient implements AiClient{
    public static final String MODEL = "text-davinci-003";
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.8;
    public static final boolean ECHO = true;

    @Value("${openAi.client.token}")
    private String openAiToken;

    @Override
    public String getToken() {
        return openAiToken;
    }

    @Override
    public CompletionRequest buildCompletionRequest(String request) {
        return CompletionRequest.builder()
                .prompt(request)
                .model(MODEL)
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .echo(ECHO)
                .build();
    }
}
