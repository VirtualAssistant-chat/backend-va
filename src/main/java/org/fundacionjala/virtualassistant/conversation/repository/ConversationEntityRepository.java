package org.fundacionjala.virtualassistant.conversation.repository;

import org.fundacionjala.virtualassistant.conversation.model.ConversationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationEntityRepository extends JpaRepository<ConversationEntity, Long> {
    Page<ConversationEntity> findByIdUserAndIdContext(Long userId, Long contextId, Pageable pageable);
}
