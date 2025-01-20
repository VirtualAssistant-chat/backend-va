package org.fundacionjala.virtualassistant.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    RedisTemplate<String, Object> redisTemplate;
    private static final int DELETION_TIME = 3600;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToRedis(String key, byte[] value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value, DELETION_TIME, TimeUnit.SECONDS);
    }

    public byte[] getFromRedis(String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(key);

        if (value instanceof String) {
            return Base64.getDecoder().decode((String) value);
        } else if (value instanceof byte[]) {
            return (byte[]) value;
        } else {
            return null;
        }
    }
}
