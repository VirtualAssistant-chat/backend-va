package org.fundacionjala.virtualassistant.conversation.service;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.fundacionjala.virtualassistant.conversation.model.ConversationResponse;
import org.fundacionjala.virtualassistant.conversation.repository.ConversationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationEntityRepository repository;

    @Autowired
    public ConversationService(ConversationEntityRepository repository) {
        this.repository = repository;
    }

    public List<ConversationResponse> getAllByUserAndContext(Long userId, Long contextId,
                                                             int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ConversationEntity> result = repository.findByIdUserAndIdContext(userId, contextId, pageable);
        return result.getContent()
                .stream()
                .map(this::parseToResponse)
                .collect(Collectors.toList());
    }

    private ConversationResponse parseToResponse(ConversationEntity entity) {
        return ConversationResponse.builder()
                .idRequest(entity.getIdRequest())
                .textRequest(entity.getTextRequest())
                .dateRequest(entity.getDateRequest())
                .idAudio(entity.getIdAudio())
                .textResponse(entity.getTextResponse())
                .dateResponse(entity.getDateResponse())
                .build();
    }
}
