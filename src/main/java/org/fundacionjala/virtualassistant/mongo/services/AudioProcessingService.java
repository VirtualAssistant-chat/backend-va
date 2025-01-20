package org.fundacionjala.virtualassistant.mongo.services;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOpenAiImplementation;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AudioProcessingService {
    private RecordingService recordingService;
    private AsrOpenAiImplementation asrOpenAiImplementation;
    private AudioService audioService;

    @Transactional(rollbackFor = Exception.class)
    public TextRequestResponse processAudio(RecordingRequest recordingRequest)
            throws RecordingException, FileSaveException, ParserException,
            IntentException, TextRequestException, IOException {
        String idMongo = saveOnRedisAndMongo(recordingRequest);
        try {
            return asrOpenAiImplementation
                    .asrOpenAIResponse(recordingRequest, idMongo);
        } catch (IOException | IntentException | ParserException | TextRequestException e) {
            recordingService.deleteRecording(idMongo);
            throw e;
        }
    }

    private String saveOnRedisAndMongo(RecordingRequest recordingRequest)
            throws FileSaveException, RecordingException {
        audioService.save(recordingRequest.getAudioFile());
        RecordingResponse recordingResponse = recordingService.saveRecording(recordingRequest);
        return recordingResponse.getIdRecording();
    }
}
