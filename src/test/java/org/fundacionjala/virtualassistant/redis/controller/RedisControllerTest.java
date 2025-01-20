package org.fundacionjala.virtualassistant.redis.controller;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RedisControllerTest {

    @InjectMocks
    private AudioController audioController;

    @Mock
    private AudioService audioService;

    private static final String FILE_NAME = "file";
    private static final String ORIGINAL_FILE_NAME = "Question.wav";
    private static final String CONTENT_TYPE = "audio/wav";
    private static final String MOCK_ID = "mock-id";
    MockMultipartFile audioExample;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File audioFile = new ClassPathResource(ORIGINAL_FILE_NAME).getFile();
        audioExample = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
    }

    @Test
    void GivenValidAudioFileWhenAddingAudioThenAudioIsAdded() throws Exception {
        Audio mockAudio = new Audio();
        when(audioService.save(any(MultipartFile.class))).thenReturn(mockAudio);
        Audio response = audioController.addAudio(audioExample);
        assertEquals(mockAudio, response);
    }

    @Test
    void GivenValidIdWhenGettingAudioThenReturnsAudioBytes() throws Exception {
        byte[] mockAudioBytes = ORIGINAL_FILE_NAME.getBytes();
        when(audioService.findById(MOCK_ID)).thenReturn(mockAudioBytes);
        byte[] response = audioController.getAudio(MOCK_ID);
        assertArrayEquals(mockAudioBytes, response);
    }

    @Test
    void GivenValidAudioFileWhenErrorOccursAddingThenThrowsFileSaveException() throws Exception {
        when(audioService.save(any(MultipartFile.class))).thenThrow(new FileSaveException("save file failed"));
        assertThrows(FileSaveException.class, () -> audioController.addAudio(audioExample));
    }

    @Test
    void GivenValidIdWhenAudioNotFoundThenThrowsRedisDataNotFoundException() throws Exception {
        when(audioService.findById(MOCK_ID)).thenThrow(new RedisDataNotFoundException("Audio not found"));
        assertThrows(RedisDataNotFoundException.class, () -> audioController.getAudio(MOCK_ID));
    }
}