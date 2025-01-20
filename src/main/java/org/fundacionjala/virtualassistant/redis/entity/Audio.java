package org.fundacionjala.virtualassistant.redis.entity;

import lombok.Data;

import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Audio")
public class Audio {
    private String id;
    private byte[] audioFile;
}
