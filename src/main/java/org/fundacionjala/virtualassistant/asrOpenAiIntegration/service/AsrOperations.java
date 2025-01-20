package org.fundacionjala.virtualassistant.asrOpenAiIntegration.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.mongo.controller.response.AudioResponse;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.redis.service.RedisService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AsrOperations {

    private RecordingService recordingService;
    private RedisService redisService;


    public void uploadTemporalAudio(String id) throws RecordingException {
        RecordingResponse response = recordingService.getRecording(id);
        AudioResponse audioResponse = response.getAudioResponse();

        redisService.saveToRedis(response.getIdRecording(),audioResponse.getAudioByte());
    }

}
