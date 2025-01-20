package org.fundacionjala.virtualassistant.mongo.controller;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.AudioProcessingService;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/audio")
@AllArgsConstructor
public class AudioProcessingController {
    private AudioProcessingService audioProcessingService;

    @PostMapping
    public ResponseEntity<?> processAudio(@Valid @ModelAttribute RecordingRequest recordingRequest)
            throws FileSaveException, RecordingException, ParserException,
            IntentException, TextRequestException, IOException {
        return ResponseEntity.ok(audioProcessingService.processAudio(recordingRequest));
    }
}
