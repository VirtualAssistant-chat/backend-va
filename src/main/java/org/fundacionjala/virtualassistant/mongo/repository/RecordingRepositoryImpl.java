package org.fundacionjala.virtualassistant.mongo.repository;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.exception.GeneratedDocumentException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RecordingRepositoryImpl implements RecordingRepo {

    MongoTemplate mongoTemplate;
    private static final String AUDIO_FIELD_NAME = "audio";

    public RecordingRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Recording> getAllRecordings() {
        return mongoTemplate.findAll(Recording.class);
    }

    @Override
    public Recording getRecording(String idRecording) {
        Query query = generateQueryCriteria(idRecording);
        return mongoTemplate.findOne(query, Recording.class);
    }

    @Override
    public Recording getRecordingToUser(String idRecording, Long idUser, Long idChat) {
        Query query = new Query(Criteria.where("idUser").is(idUser).and("idChat").is(idChat).and("idRecording").is(idRecording));
        return mongoTemplate.findOne(query, Recording.class);
    }

    @Override
    public List<Recording> getAllRecordingsToUser(Long idUser, Long idChat) {
        Query query = new Query(Criteria.where("idUser").is(idUser).and("idChat").is(idChat));
        return mongoTemplate.find(query, Recording.class);
    }

    @Override
    public long deleteRecording(String idRecording) {
        Query query = generateQueryCriteria(idRecording);
        Recording recordingToDelete = mongoTemplate.findOne(query, Recording.class);
        AtomicLong asw = new AtomicLong();
        Optional.ofNullable(recordingToDelete).ifPresent(recording -> {
            var deleteResult = mongoTemplate.remove(query, Recording.class);
            asw.set(deleteResult.getDeletedCount());
        });
        return asw.get();
    }

    @Override
    public Recording saveRecording(Long idUser, Long idChat, MultipartFile audioFile) throws RecordingException {
        Document metadata = generateDocumentRecording(audioFile);
        Recording recording = new Recording(idUser, idChat, metadata);
        try {
            return mongoTemplate.save(recording);
        } catch (IllegalArgumentException e) {
            throw new RecordingException(e.getMessage(), e);
        }
    }

    private Document generateDocumentRecording(MultipartFile file) throws GeneratedDocumentException {
        try {
            byte[] audioBytes = file.getBytes();
            String encodedAudio = Base64.getEncoder().encodeToString(audioBytes);
            return new Document(AUDIO_FIELD_NAME, encodedAudio);
        } catch (IOException e) {
            throw new GeneratedDocumentException(e.getMessage(), e);
        }
    }

    private Query generateQueryCriteria(String idRecording){
        return new Query(Criteria.where("idRecording").is(idRecording));
    }
}
