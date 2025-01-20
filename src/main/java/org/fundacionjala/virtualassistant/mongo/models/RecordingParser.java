package org.fundacionjala.virtualassistant.mongo.models;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.response.AudioResponse;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import javax.validation.constraints.NotEmpty;
import java.util.Base64;

import static java.util.Objects.isNull;

public class RecordingParser {

    private static final String AUDIO_FIELD_NAME = "audio";
    private static final String AUDIO_EXTENSION = ".wav";
    private static final String MESSAGE_DOCUMENT_NULL = "The document to be converted to audioResponse is null.";

    public static RecordingResponse parseToRecordingResponseFrom(Recording recording)
            throws RecordingException {
        if (isNull(recording)) {
            throw new RecordingException(RecordingException.MESSAGE_RECORDING_NULL);
        }
        return RecordingResponse.builder()
                .idRecording(recording.getIdRecording())
                .idUser(recording.getIdUser())
                .idChat(recording.getIdChat())
                .audioResponse(convertDocumentToAudioResponse(recording.getAudioFile(),
                        generateNameAudio(recording.getIdRecording())))
                .build();
    }

    private static AudioResponse convertDocumentToAudioResponse(Document document, String nameAudio) throws RecordingException {
        if (isNull(document)) {
            throw new RecordingException(MESSAGE_DOCUMENT_NULL);
        }
        String encodedAudio = document.getString(AUDIO_FIELD_NAME);
        byte[] audioBytes = Base64.getDecoder().decode(encodedAudio);
        return AudioResponse.builder()
                .nameAudio(nameAudio)
                .audioByte(audioBytes)
                .build();
    }
    
    private static String generateNameAudio(@NotEmpty String name){
        return name + AUDIO_EXTENSION;
    }
}
