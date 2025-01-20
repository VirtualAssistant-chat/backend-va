package org.fundacionjala.virtualassistant.conversation.controller;

import org.fundacionjala.virtualassistant.conversation.model.ConversationResponse;
import org.fundacionjala.virtualassistant.conversation.service.ConversationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversationController.class)
public class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationService conversationService;

    @Test
    public void statusShouldBeSuccessfulIfGetRequestReturnsEmptyList() throws Exception {
        Long userId = 1L;
        Long contextId = 2L;
        List<ConversationResponse> entities = new ArrayList<>();
        when(conversationService.getAllByUserAndContext(userId, contextId, 0, 10))
                .thenReturn(entities);

        mockMvc.perform(get("/users/{userId}/contexts/{contextId}", userId, contextId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void shouldReturnFistResultWithPageSizeEquals1() throws Exception {
        Long userId = 1L;
        Long contextId = 2L;
        List<ConversationResponse> entities = new ArrayList<>();
        entities.add(getOneConversationResponse());

        when(conversationService.getAllByUserAndContext(userId, contextId, 0, 1))
                .thenReturn(entities);

        mockMvc.perform(get("/users/{userId}/contexts/{contextId}?page=0&size=1", userId, contextId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.idRequest == '1')]").exists());
    }

    private ConversationResponse getOneConversationResponse() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return ConversationResponse.builder()
                .idRequest(1L)
                .textRequest("text request")
                .dateRequest(zonedDateTime)
                .idAudio("123-xxxxx")
                .textResponse("Text Response")
                .dateResponse(zonedDateTime)
                .build();
    }
}
