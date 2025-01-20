package org.fundacionjala.virtualassistant.services.mongo;

import org.bson.Document;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.mongo.controller.response.AudioResponse;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.ConvertedDocumentToFileException;
import org.fundacionjala.virtualassistant.mongo.exception.GeneratedDocumentException;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.models.Recording;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepo;
import org.fundacionjala.virtualassistant.mongo.repository.RecordingRepositoryImpl;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecordingServiceImplTest {
  private RecordingService service;
  private RecordingRepo recordingRepo;
  private long idUser;
  private long idChat;
  MockMultipartFile mockFile;
  private static final String FILE_NAME = "test";
  private static final String ORIGINAL_FILE_NAME = "test.wav";

  @BeforeEach
  void setUp() {
    idUser = 12L;
    idChat = 13L;
    mockFile = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME,
            MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[10]);
    recordingRepo = mock(RecordingRepositoryImpl.class);
    service = new RecordingService(recordingRepo, new Either<>());
  }

  @Test
  @DisplayName("Save a RecordingRequest and return a RecordingResponse")
  void givenARecordingRequestInDBWhenSavingThenItWillReturnARecordingResponse()
          throws RecordingException, IOException {
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    AudioResponse audioResponse = new AudioResponse(ORIGINAL_FILE_NAME, mockFile.getBytes());

    RecordingResponse recordingResponse = new RecordingResponse("Id", idUser, idChat, audioResponse);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenReturn(new Recording(idUser, idChat, new Document("audio", "")));

    RecordingResponse result = service.saveRecording(recordingRequest);
    verify(recordingRepo).saveRecording(idUser, idChat, mockFile);
    assertNotNull(result);
    assertEquals(recordingResponse.getIdUser(), result.getIdUser());
    assertEquals(recordingResponse.getIdChat(), result.getIdChat());
    assertNotNull(result.getAudioResponse());
  }

  @Test
  @DisplayName("Throw GeneratedDocumentException")
  void givenARecordingRequestWhenTryingToSaveThenThrowAnGeneratedDocumentException()
          throws RecordingException {
    String message = "Not possible to generate the document";
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(new GeneratedDocumentException(message));

    RecordingException recordingException = assertThrows(GeneratedDocumentException.class, () -> {
      service.saveRecording(recordingRequest);
    });
    assertEquals(message, recordingException.getMessage());
  }

  @Test
  @DisplayName("Throw RecordingException")
  void givenARecordingRequestWhenTryingToSaveThenThrowRecordingException()
          throws RecordingException {
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(RecordingException.class);

    assertThrows(RecordingException.class, () -> {
      service.saveRecording(recordingRequest);
    });
  }

  @Test
  @DisplayName("Throw ConvertedDocumentToFileException")
  void givenARecordingRequestWhenTryingToSaveThenThrowConvertedDocumentToFileException()
          throws RecordingException {
    RecordingService serviceMock = mock(RecordingService.class);
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(serviceMock.saveRecording(any(RecordingRequest.class)))
            .thenThrow(ConvertedDocumentToFileException.class);

    assertThrows(ConvertedDocumentToFileException.class, () -> {
      serviceMock.saveRecording(recordingRequest);
    });
  }

  @Test
  @DisplayName("Throw NullPointerException")
  void givenARecordingRequestWhenMockFileIsNullThenThrowNullPointerException()
          throws RecordingException {
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenThrow(RecordingException.class);
    assertThrows(RecordingException.class, () -> {
      service.saveRecording(recordingRequest);
    });
  }

  @Test
  @DisplayName("Save a mock file with 1000 bytes of size")
  void givenARecordingRequestWhenMockFileIs1000BytesAndSaveItThenReturnARecordingResponse()
          throws RecordingException, IOException {
    mockFile = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME,
            MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[1000]);
    RecordingRequest recordingRequest = new RecordingRequest(idUser, idChat, mockFile);
    AudioResponse audioResponse = new AudioResponse(ORIGINAL_FILE_NAME, mockFile.getBytes());

    RecordingResponse recordingResponse = new RecordingResponse("Id", idUser, idChat, audioResponse);
    String encodedAudio = Base64.getEncoder().encodeToString(mockFile.getBytes());
    when(recordingRepo.saveRecording(anyLong(), anyLong(), any(MultipartFile.class)))
            .thenReturn(new Recording(idUser, idChat, new Document("audio", encodedAudio)));

    RecordingResponse result = service.saveRecording(recordingRequest);

    verify(recordingRepo).saveRecording(idUser, idChat, mockFile);
    assertNotNull(result);
    assertEquals(recordingResponse.getAudioResponse().getAudioByte().length,recordingRequest.getAudioFile().getBytes().length );
    assertEquals(recordingResponse.getIdUser(), result.getIdUser());
    assertEquals(recordingResponse.getIdChat(), result.getIdChat());
    assertNotNull(result.getAudioResponse());
  }
}