package org.fundacionjala.virtualassistant.redis.service;

import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.repository.AudioRepository;
import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AudioService {

    AudioRepository audioRepository;
    RedisService redisService;
    public AudioService(AudioRepository audioRepository, RedisService redisService){
        this.audioRepository = audioRepository;
        this.redisService = redisService;
    }

    public Audio save(MultipartFile file) throws FileSaveException {
        try {
            Audio audio = new Audio();
            audio.setId(UUID.randomUUID().toString());
            audio.setAudioFile(file.getBytes());
            Audio savedAudio = audioRepository.save(audio);
            redisService.saveToRedis(savedAudio.getId(), savedAudio.getAudioFile());
            return savedAudio;
        } catch (IOException e) {
            throw new FileSaveException(FileSaveException.FAILED_MESSAGE);
        }
    }

    public byte[] findById(String id) throws RedisDataNotFoundException {
        byte[] result = redisService.getFromRedis(id);
        if (result == null) {
            throw new RedisDataNotFoundException(RedisDataNotFoundException.FAILED_MESSAGE_EXPECTED + id);
        }
        return result;
    }
}
