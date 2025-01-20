package org.fundacionjala.virtualassistant.redis.repository;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.springframework.data.repository.CrudRepository;

public interface AudioRepository extends CrudRepository<Audio, String> {
}
