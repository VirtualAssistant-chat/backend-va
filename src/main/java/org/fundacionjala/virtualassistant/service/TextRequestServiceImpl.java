package org.fundacionjala.virtualassistant.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TextRequestServiceImpl implements TextRequestService{
    RequestEntityRepository requestEntityRepository;

    @Override
    public List<RequestEntity> getTextRequestByUserAndContext(Long id, Long contextId) {
        return requestEntityRepository.findAllByIdUserAndContextEntityIdContext(id, contextId);
    }
}