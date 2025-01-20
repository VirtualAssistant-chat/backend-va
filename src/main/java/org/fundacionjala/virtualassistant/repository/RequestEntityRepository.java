package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestEntityRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByIdUserAndContextEntityIdContext(long idUser, long idContext);
}
