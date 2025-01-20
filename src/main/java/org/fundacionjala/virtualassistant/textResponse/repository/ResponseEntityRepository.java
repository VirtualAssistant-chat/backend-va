package org.fundacionjala.virtualassistant.textResponse.repository;

import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseEntityRepository extends JpaRepository<ResponseEntity, Long> {
}
