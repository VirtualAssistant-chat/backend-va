package org.fundacionjala.virtualassistant.asrOpenAiIntegrationTests.service;


import org.fundacionjala.virtualassistant.asrOpenAiIntegration.service.AsrOperations;
import org.fundacionjala.virtualassistant.mongo.controller.response.AudioResponse;
import org.fundacionjala.virtualassistant.mongo.controller.response.RecordingResponse;
import org.fundacionjala.virtualassistant.mongo.exception.RecordingException;
import org.fundacionjala.virtualassistant.mongo.services.RecordingService;
import org.fundacionjala.virtualassistant.redis.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AsrOperationsTest {

    @InjectMocks
    private AsrOperations asrOperations;

    @Mock
    private RecordingService recordingService;

    @Mock
    private RedisService redisService;

    private final String ID_TEST = "1";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GivenValidIdWhenUploadAudioThenSuccess() throws RecordingException {
        RecordingResponse recordingResponse = mock(RecordingResponse.class);
        AudioResponse audioResponse = mock(AudioResponse.class);
        byte[] audioBytes = new byte[]{};

        when(recordingService.getRecording(ID_TEST)).thenReturn(recordingResponse);
        when(recordingResponse.getAudioResponse()).thenReturn(audioResponse);
        when(audioResponse.getAudioByte()).thenReturn(audioBytes);

        asrOperations.uploadTemporalAudio(ID_TEST);

        verify(recordingService).getRecording(ID_TEST);
        verify(redisService).saveToRedis(recordingResponse.getIdRecording(), audioBytes);
    }
    @Test
    void GivenRecordingServiceThrowsExceptionWhenUploadTemporalAudioCalledThenRecordingExceptionIsThrown() throws RecordingException {
        when(recordingService.getRecording(ID_TEST)).thenThrow(RecordingException.class);

        assertThrows(RecordingException.class, () -> asrOperations.uploadTemporalAudio(ID_TEST));
        verify(recordingService).getRecording(ID_TEST);
        verify(redisService, never()).saveToRedis(any(), any());
    }

}