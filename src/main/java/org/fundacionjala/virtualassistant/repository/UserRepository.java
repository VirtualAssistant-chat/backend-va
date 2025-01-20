package org.fundacionjala.virtualassistant.repository;

import org.fundacionjala.virtualassistant.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
