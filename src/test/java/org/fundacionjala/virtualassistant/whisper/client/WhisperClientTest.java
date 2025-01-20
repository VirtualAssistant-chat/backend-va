package org.fundacionjala.virtualassistant.whisper.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WhisperClientTest {

    private WhisperClient whisperClient;
    private static final String URL = "http://localhost:8000";
    private static final String POST_ENDPOINT = "/record";
    private static final String FILE_NAME = "file";
    private static final String CONTENT_TYPE = "audio/wav";

    @BeforeEach
    void setUp() {
        whisperClient = mock(WhisperClient.class);
        whisperClient.setUrl(URL);
        whisperClient.setPostEndpoint(POST_ENDPOINT);
    }

    @Test
    void shouldReturnATextFromAnAudioFileQuestion() throws IOException {
        String fileName = "Question.wav";
        File audioFile = new ClassPathResource(fileName).getFile();
        MockMultipartFile mockAudio = new MockMultipartFile(FILE_NAME, fileName, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
        String expected = "\"How is it weather today?\"";
        when(whisperClient.convertToText(mockAudio)).thenReturn(expected);

        String actual = whisperClient.convertToText(mockAudio);

        verify(whisperClient).convertToText(mockAudio);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnATextFromAnAudioFileOf10Seconds() throws IOException {
        String fileName = "10sec.wav";
        File audioFile = new ClassPathResource(fileName).getFile();
        MockMultipartFile mockAudio = new MockMultipartFile(FILE_NAME, fileName, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
        String expected = "\"Last year Tesla shares were on fire when they went over $1,000 per share.\"";
        when(whisperClient.convertToText(mockAudio)).thenReturn(expected);

        String actual = whisperClient.convertToText(mockAudio);

        verify(whisperClient).convertToText(mockAudio);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnATextFromAnAudioFileOf20Seconds() throws IOException {
        String fileName = "20sec.wav";
        File audioFile = new ClassPathResource(fileName).getFile();
        MockMultipartFile mockAudio = new MockMultipartFile(FILE_NAME, fileName, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
        String expected = "\"Hi there, this is Valentine and if you can see this text, it means you have successfully used the Whisper API from OpenAI to transcribe this audio to text. I would really appreciate it if you could give this video a thumbs up and subscribe to my channel. Your support helps me create more tutorials like this one. Thanks a lot.\"";
        when(whisperClient.convertToText(mockAudio)).thenReturn(expected);

        String actual = whisperClient.convertToText(mockAudio);

        verify(whisperClient).convertToText(mockAudio);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnATextFromAnAudioFileOf30Seconds() throws IOException {
        String fileName = "30sec.wav";
        File audioFile = new ClassPathResource(fileName).getFile();
        MockMultipartFile mockAudio = new MockMultipartFile(FILE_NAME, fileName, CONTENT_TYPE, Files.readAllBytes(audioFile.toPath()));
        String expected = "\"A golden ring will please most any girl. The long journey home took a year. She saw a cat in the neighbor's house. A pink shell was found on the sandy beach. Small children came to see him. The grass and bushes were wet with dew. The blind man counted his old coins. A severe storm tore down the barn. She called his name many times. When you hear the bell come quickly.\"";
        when(whisperClient.convertToText(mockAudio)).thenReturn(expected);

        String actual = whisperClient.convertToText(mockAudio);

        verify(whisperClient).convertToText(mockAudio);
        assertEquals(expected, actual);
    }
}