package org.fundacionjala.virtualassistant.context.repository;

import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContextRepository extends JpaRepository<ContextEntity,Long> {

    List<ContextEntity> findByUserEntityIdUser(Long idUser);
}