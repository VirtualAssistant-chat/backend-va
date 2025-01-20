package org.fundacionjala.virtualassistant.mongo.controller;

import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.AudioConverter;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recordings")
public class RecordingController {

    RecordingService recordingService;

    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @GetMapping()
    public ResponseEntity<List<RecordingResponse>> getAllRecordings() throws RecordingException {
        List<RecordingResponse> recordings = recordingService.getAllRecordings();
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordingResponse> getRecordingID(@NotEmpty @PathVariable("id") String id) {
        try {
            Optional<RecordingResponse> recording = Optional.ofNullable(recordingService.getRecording(id));
            return recording.map(recordingResponse -> new ResponseEntity<>(recordingResponse, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RecordingException recordingException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{idUser}/chat/{idChat}")
    public ResponseEntity<List<RecordingResponse>> getRecordingsUser(@NotEmpty @PathVariable("idUser") Long idUser,
                                                                     @NotEmpty @PathVariable("idChat") Long idChat)
            throws RecordingException {
        List<RecordingResponse> recordings = recordingService.getAllRecordingsToUser(idUser, idChat);
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordingResponse> saveRecording(@Valid @ModelAttribute RecordingRequest recordingRequest)
            throws RecordingException {
        RecordingResponse savedRecording = recordingService.saveRecording(recordingRequest);
        return new ResponseEntity<>(savedRecording, HttpStatus.CREATED);
    }

    @GetMapping("/audio/download/{id}")
    public ResponseEntity<InputStreamResource> getRecordingByIdDownload(@NotEmpty @PathVariable("id") String id) throws RecordingException {
        try {
            Optional<RecordingResponse> recording = Optional.ofNullable(recordingService.getRecording(id));
            if (recording.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return AudioConverter.convertRecordingToInputStreamResource(recording);
        } catch (RecordingException recordingException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}