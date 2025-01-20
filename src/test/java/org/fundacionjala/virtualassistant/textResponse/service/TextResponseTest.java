package org.fundacionjala.virtualassistant.textResponse.service;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.repository.ResponseEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TextResponseTest {

    private ResponseEntityRepository mockRepository;
    private TextResponseService textResponseService;
    private ResponseEntity responseEntity;

    private String text;
    private long idRequest;

    @BeforeEach
    void setUp() {
        mockRepository = mock(ResponseEntityRepository.class);
        textResponseService = new TextResponseService(mockRepository);

        text = "Test Text";
        idRequest = 123L;
        responseEntity = ResponseEntity.builder()
                .requestEntity(RequestEntity.builder()
                        .idRequest(idRequest)
                        .build())
                .text(text)
                .date(ZonedDateTime.now())
                .build();
    }

    @Test
    public void givenResponseEntity_whenSaveTextResponseService_thenSaveTextResponse()
            throws ParserException {

        when(mockRepository.save(any(ResponseEntity.class))).thenReturn(responseEntity);

        TextResponse result = textResponseService.save(idRequest, text);

        assertNotNull(result);
        assertSame(result.getText(), responseEntity.getText());
        assertSame(result.getIdResponse(), responseEntity.getIdResponse());
        assertSame(result.getIdRequest(), responseEntity.getRequestEntity().getIdRequest());
    }

    @Test
    void shouldSaveAResponseEntityForAParameterResponse() throws ParserException {
        ParameterResponse parameterResponse = ParameterResponse.builder()
                .text(text)
                .request(TextRequestResponse.builder()
                        .idRequest(idRequest)
                        .build())
                .build();
        when(mockRepository.save(any(ResponseEntity.class))).thenReturn(responseEntity);
        TextResponse textResponse = textResponseService.save(parameterResponse);

        verify(mockRepository).save(any(ResponseEntity.class));
        assertNotNull(textResponse);
    }

    @Test
    void shouldThrowParserExceptionForSaveAParameterResponseNull() {
        when(mockRepository.save(any(ResponseEntity.class))).thenReturn(responseEntity);

        assertThrows(ParserException.class, () -> textResponseService.save(null));
    }

    @Test
    void shouldThrowParserExceptionForResponseEntityNull() {
        ParameterResponse parameterResponse = ParameterResponse.builder()
                .text(text)
                .request(TextRequestResponse.builder()
                        .idRequest(idRequest)
                        .build())
                .build();
        when(mockRepository.save(any(ResponseEntity.class))).thenReturn(null);

        assertThrows(ParserException.class, () -> textResponseService.save(parameterResponse));
    }
}
