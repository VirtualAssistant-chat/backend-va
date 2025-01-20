package org.fundacionjala.virtualassistant.openai;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.fundacionjala.virtualassistant.clients.openai.client.OpenAiClient;
import org.fundacionjala.virtualassistant.clients.openai.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ChatServiceTest {

    private OpenAiClient mockOpenAiClient;
    private ChatService chatService;
    private static final String REQUEST = "hi how are you";
    private String getToken(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(OpenAiClient.class);
        context.refresh();
        OpenAiClient openAiClient = context.getBean(OpenAiClient.class);
        return openAiClient.getToken();
    }

    @BeforeEach
    void setUp() {
        OpenAiService mockOpenAiService = mock(OpenAiService.class);
        mockOpenAiClient = mock(OpenAiClient.class);
        chatService = new ChatService(mockOpenAiClient, mockOpenAiService);

        when(mockOpenAiClient.getToken()).thenReturn(getToken());
        when(mockOpenAiClient.buildCompletionRequest(REQUEST)).thenCallRealMethod();

        CompletionChoice completionChoice = new CompletionChoice();
        completionChoice.setText(REQUEST);

        CompletionResult completionResult = new CompletionResult();
        completionResult.setChoices(Collections.singletonList(completionChoice));

        when(mockOpenAiService.createCompletion(any(CompletionRequest.class)))
                .thenReturn(completionResult);
    }

    @Test
    void shouldReturnAStringAndCallOpenAiClientMethodsWhenARequestIsUsedInChatService() {
        String result = chatService.chat(REQUEST);

        verify(mockOpenAiClient, times(2)).buildCompletionRequest(REQUEST);
        assertNotNull(result, "result should not be null");
    }
}