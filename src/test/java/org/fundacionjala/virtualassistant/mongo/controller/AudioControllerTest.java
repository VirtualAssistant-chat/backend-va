package org.fundacionjala.virtualassistant.mongo.controller;

import org.fundacionjala.virtualassistant.mongo.services.AudioProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AudioProcessingController.class)
@AutoConfigureMockMvc
class AudioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudioProcessingService audioProcessingService;
    private MockMultipartFile audioFile;
    private static final Long ID_USER = 12L;
    private static final Long ID_CHAT = 12L;

    @BeforeEach
    void setUp() {
        audioFile = new MockMultipartFile("audioFile", "test.wav",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[100]);
    }

    @Test
    void shouldSaveTheRecordingRequestAndProcessIt() throws Exception {
        mockMvc.perform(multipart("/audio")
                        .file(audioFile)
                        .param("idUser", Long.toString(ID_USER))
                        .param("idChat", Long.toString(ID_CHAT))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnABadRequestForProcessAnEmptyMultipartFile() throws Exception {
        audioFile = new MockMultipartFile("test", "test.wav",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new byte[100]);
        mockMvc.perform(multipart("/audio")
                        .file(audioFile)
                        .param("idUser", Long.toString(ID_USER))
                        .param("idChat", Long.toString(ID_CHAT))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnABadRequestForProcessAnEmptyIdUser() throws Exception {
        mockMvc.perform(multipart("/audio")
                        .file(audioFile)
                        .param("idChat", Long.toString(ID_CHAT))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnABadRequestForProcessAnEmptyIdChat() throws Exception {
        mockMvc.perform(multipart("/audio")
                        .file(audioFile)
                        .param("idUser", Long.toString(ID_USER))
                )
                .andExpect(status().isBadRequest());
    }

}