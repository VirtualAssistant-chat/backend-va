package org.fundacionjala.virtualassistant.clients.openai.service;

import com.theokanning.openai.OpenAiError;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;
import org.fundacionjala.virtualassistant.clients.openai.exeption.ChatServiceOpenAiHttpException;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Service
public class ChatService {
    private OpenAiClient openAiClient;
    private OpenAiService openAiService;
    private final String INVALID_TOKEN_ERROR_MESSAGE = "Sorry, a response could not be generated at this time due to a problem with your access token. This may be due to an invalid or expired token. Please verify your token and make sure it is valid and has the necessary permissions to access the chat service. If you need help obtaining a valid token or have any questions you can access this link: https://platform.openai.com/docs/introduction";
    private final String INVALID_TOKEN_ERROR_LINK = "https://beta.openai.com/docs/api-reference/errors/invalid-token";
    private final String INVALID_TOKEN_ERROR_TYPE = "invalid_token";
    private final String INVALID_TOKEN_ERROR_TITLE = "Invalid token";
    private final String ERROR_REQUEST_MESSAGE = "The response could not be generated";
    private final int ERROR_REQUEST_STATUS_CODE = 401;


    @Autowired
    public ChatService(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
        this.openAiService = new OpenAiService(openAiClient.getToken());
    }

    public ChatService(OpenAiClient openAiClient, OpenAiService openAiService) {
        this.openAiClient = openAiClient;
        this.openAiService = openAiService;
    }

    @NotEmpty
    @Pattern(regexp = ".*[a-zA-Z].*")
    public String chat(String request) {
        String answer = generateResponse(this.openAiService, request);
        return removePatternFromStart(answer, openAiClient.buildCompletionRequest(request).getPrompt());
    }

    private String generateResponse(OpenAiService service, String request) throws OpenAiHttpException {
        try {
            return service.createCompletion(openAiClient.buildCompletionRequest(request))
                    .getChoices()
                    .stream()
                    .map(choice -> choice.getText())
                    .collect(Collectors.joining());
        } catch (OpenAiHttpException ex) {
            handleOpenAiHttpException(ex);
        }
        return ERROR_REQUEST_MESSAGE;
    }

    private void handleOpenAiHttpException(OpenAiHttpException e) {
        OpenAiError.OpenAiErrorDetails errorDetails = new OpenAiError.OpenAiErrorDetails(
                INVALID_TOKEN_ERROR_MESSAGE,
                INVALID_TOKEN_ERROR_LINK,
                INVALID_TOKEN_ERROR_TYPE,
                INVALID_TOKEN_ERROR_TITLE);
        throw new ChatServiceOpenAiHttpException(new OpenAiError(errorDetails), e, ERROR_REQUEST_STATUS_CODE);
    }

    private String removePatternFromStart(String input, String toRemovePattern) {
        int patternFinishIndex = toRemovePattern.length();
        return input.substring(patternFinishIndex).trim();
    }
}