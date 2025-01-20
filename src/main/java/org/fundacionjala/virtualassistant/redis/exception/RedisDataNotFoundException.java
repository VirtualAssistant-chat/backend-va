package org.fundacionjala.virtualassistant.redis.exception;

public class RedisDataNotFoundException extends Exception {
    public static final String FAILED_MESSAGE_EXPECTED = "The data type in Redis is not what is expected for the key: ";

    public RedisDataNotFoundException(String message) {
        super(message);
    }

    public RedisDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
