package org.fundacionjala.virtualassistant.mongo.repository;

import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface RecordingRepo {

    Recording getRecording(String idRecording);

    long deleteRecording(String idRecording);

    List<Recording> getAllRecordingsToUser(Long idUser, Long idChat);

    List<Recording> getAllRecordings();

    Recording saveRecording(Long idUser, Long idChat, MultipartFile audioPath) throws RecordingException;

    Recording getRecordingToUser(String idRecording, Long idUser, Long idChat);
}
