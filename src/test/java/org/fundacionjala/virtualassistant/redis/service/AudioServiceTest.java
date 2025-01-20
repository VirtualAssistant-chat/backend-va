package org.fundacionjala.virtualassistant.redis.service;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.repository.AudioRepository;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class AudioServiceTest {

    @InjectMocks
    private AudioService audioService;

    @Mock
    private AudioRepository audioRepository;

    @Mock
    private RedisService redisService;

    private static final String FILE_NAME = "file";
    private static final String ORIGINAL_FILE_NAME = "Question.wav";
    private static final String CONTENT_TYPE = "audio/wav";
    private static final String MOCK_ID = "test-id";
    private static final String MOCK_NON_EXISTENT_ID = "non-existent-id";
    private MockMultipartFile mockAudio;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File audioFile = new ClassPathResource(ORIGINAL_FILE_NAME).getFile();
        mockAudio = new MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
    }

    @Test
    void GivenValidAudioFileWhenSavingAudioThenAudioSaved() throws FileSaveException, IOException {
        Audio audio = new Audio();
        audio.setId(MOCK_ID);
        audio.setAudioFile(mockAudio.getBytes());

        when(audioRepository.save(any(Audio.class))).thenReturn(audio);

        Audio savedAudio = audioService.save(mockAudio);

        verify(audioRepository).save(any(Audio.class));
        verify(redisService).saveToRedis(MOCK_ID, mockAudio.getBytes());

        assertEquals(audio.getId(), savedAudio.getId());
    }

    @Test
    void GivenValidAudioIdWhenGettingAudioByIdThenAudioReturnsBytes() throws RedisDataNotFoundException, IOException {
        when(redisService.getFromRedis(MOCK_ID)).thenReturn(mockAudio.getBytes());

        byte[] audioData = audioService.findById(MOCK_ID);

        verify(redisService).getFromRedis(MOCK_ID);
        assertArrayEquals(mockAudio.getBytes(), audioData);
    }

    @Test
    void GivenInvalidAudioFileWhenSavingAudioThenNottingSavedThrowsException() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);

        when(mockFile.getBytes()).thenThrow(new IOException("Simulated IOException"));
        verify(audioRepository, never()).save(any(Audio.class));
        verify(redisService, never()).saveToRedis(anyString(), any());

        assertThrows(FileSaveException.class, () -> { audioService.save(mockFile); });
    }

    @Test
    void GivenInvalidAudioIdWhenGettingAudioByIdThenAudioThrowsException() {
        when(redisService.getFromRedis(MOCK_NON_EXISTENT_ID)).thenReturn(null);
        assertThrows(RedisDataNotFoundException.class, () -> audioService.findById(MOCK_NON_EXISTENT_ID));
    }
}