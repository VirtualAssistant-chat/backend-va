package org.fundacionjala.virtualassistant.conversation.service;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.fundacionjala.virtualassistant.conversation.model.ConversationResponse;
import org.fundacionjala.virtualassistant.conversation.repository.ConversationEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    @Mock
    private ConversationEntityRepository conversationRepository;

    @InjectMocks
    private ConversationService conversationService;
    private List<ConversationEntity> conversations;
    private List<ConversationResponse> expected;

    @BeforeEach
    public void setUp() {
        conversations = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    public void shouldGetConversationsResponse() {
        Long userId = 1L;
        Long contextId = 2L;
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page,pageSize);
        Page<ConversationEntity> conversationPage = new PageImpl<>(conversations, pageable, 0);
        when(conversationRepository.findByIdUserAndIdContext(userId, contextId, pageable))
                .thenReturn(conversationPage);

        List<ConversationResponse> result = conversationService.getAllByUserAndContext(userId, contextId,page,pageSize);
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
