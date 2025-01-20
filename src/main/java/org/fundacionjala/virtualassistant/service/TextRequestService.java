package org.fundacionjala.virtualassistant.service;

import org.fundacionjala.virtualassistant.models.RequestEntity;

import java.util.List;

public interface TextRequestService {
    List<RequestEntity> getTextRequestByUserAndContext(Long id, Long contextId);
}