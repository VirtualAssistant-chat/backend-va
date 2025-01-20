package org.fundacionjala.virtualassistant.textResponse.controller;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResponseControllerTest {
    @InjectMocks
    private ResponseController controller;

    @Mock
    private TextResponseService service;

    @Test
    public void statusShouldBeCreated() throws ParserException {
        String text = "Test Text Response";
        long idRequest = 123L;

        ParameterResponse parameter = ParameterResponse.builder()
                .request(TextRequestResponse.builder().idRequest(idRequest).build())
                .text(text)
                .build();

        TextResponse response = TextResponse.builder()
                .idRequest(idRequest)
                .text(text)
                .date(ZonedDateTime.now())
                .build();

        when(service.save(parameter)).thenReturn(response);

        ResponseEntity<TextResponse> result = controller.createTextResponse(parameter);

        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
}
