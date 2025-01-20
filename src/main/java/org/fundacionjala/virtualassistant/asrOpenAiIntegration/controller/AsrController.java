package org.fundacionjala.virtualassistant.asrOpenAiIntegration.controller;

import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOpenAiImplementation;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOperations;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.BASE64DecodedMultipartFile;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.redis.service.RedisService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/asrOpenAi")
public class AsrController {
    private AsrOperations asrOperations;
    private RedisService redisService;
    private AsrOpenAiImplementation asrOpenAiImplementation;

    @Autowired
    public AsrController(AsrOperations asrOperations, RedisService redisService, AsrOpenAiImplementation asrOpenAiImplementation) {
        this.asrOperations = asrOperations;
        this.redisService = redisService;
        this.asrOpenAiImplementation = asrOpenAiImplementation;
    }

    @GetMapping("/{id}")
    public String uploadAudio(@PathVariable String id) throws RecordingException, IOException, IntentException {
        asrOperations.uploadTemporalAudio(id);
        byte[] byteArray = redisService.getFromRedis(id);
        MultipartFile multipartFile = new BASE64DecodedMultipartFile(byteArray, "audio.wav");

        return asrOpenAiImplementation.asrOpenAIResponse(multipartFile);
    }
}