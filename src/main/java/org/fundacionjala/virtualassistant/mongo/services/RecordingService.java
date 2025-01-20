package org.fundacionjala.virtualassistant.mongo.services;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.models.RecordingParser;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class RecordingService {
    private RecordingRepo recordingRepo;
    private ProcessorEither<Exception, RecordingResponse> processorEither;
    private static final String AUDIO_EXTENSION = ".wav";
    private static final String MESSAGE_EITHER_NULL = "The either processor is null";

    public RecordingResponse getRecording(String idRecording) throws RecordingException {
        Recording recording = recordingRepo.getRecording(idRecording);
        return RecordingParser.parseToRecordingResponseFrom(recording);
    }

    public RecordingResponse getRecordingToUserChat(String idRecording, Long idUser, Long idChat) throws RecordingException {
        Recording recording = recordingRepo.getRecordingToUser(idRecording, idUser, idChat);
        return RecordingParser.parseToRecordingResponseFrom(recording);
    }

    public long deleteRecording(String idRecording) {
        return recordingRepo.deleteRecording(idRecording);
    }

    public List<RecordingResponse> getAllRecordingsToUser(Long idUser, Long idChat)
            throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordingsToUser(idUser, idChat);
        return convertListRecordingsToListResponse(recordings);
    }

    public List<RecordingResponse> getAllRecordings() throws RecordingException {
        List<Recording> recordings = recordingRepo.getAllRecordings();
        return convertListRecordingsToListResponse(recordings);
    }

    public RecordingResponse saveRecording(RecordingRequest request) throws RecordingException {
        verifyRecordingRequest(request);
        Recording recording = recordingRepo.saveRecording(request.getIdUser(), request.getIdChat(),
                request.getAudioFile());
        return RecordingParser.parseToRecordingResponseFrom(recording);
    }

    private void verifyRecordingRequest(RecordingRequest request) throws RecordingException {
        if (isNull(request)) {
            throw new RecordingException(RecordingException.MESSAGE_RECORDING_REQUEST_NULL);
        }
        if (request.getAudioFile().isEmpty()) {
            throw new RecordingException(RecordingException.MESSAGE_NULL_AUDIO_FILE);
        }
        if (!validateWavFile(request.getAudioFile())) {
            throw new RecordingException(RecordingException.MESSAGE_NOT_WAV);
        }
    }

    private List<RecordingResponse> convertListRecordingsToListResponse(List<Recording> recordings)
            throws RecordingException {
        if (isNull(processorEither)) {
            throw new RecordingException(MESSAGE_EITHER_NULL);
        }
        return recordings.stream()
                .map(processorEither.lift(recording -> {
                    try {
                        return Either.right(RecordingParser.parseToRecordingResponseFrom(recording));
                    } catch (RecordingException exception) {
                        return Either.left(exception);
                    }
                }))
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    private boolean validateWavFile(MultipartFile audioFile) {
        return Optional.ofNullable(audioFile.getOriginalFilename())
                .map(filename -> filename.toLowerCase().endsWith(AUDIO_EXTENSION))
                .orElse(false);
    }
}