package org.fundacionjala.virtualassistant.user.repository;

import org.fundacionjala.virtualassistant.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "contextEntities")
    Optional<UserEntity> findByIdUser(Long id);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.contextEntities")
    List<UserEntity> findAllEager();
}
