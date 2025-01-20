package org.fundacionjala.virtualassistant.textrequest.controller;

import org.fundacionjala.virtualassistant.clients.openai.component.RequestComponent;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextRequestControllerTest {
    @Mock
    private RequestEntityRepository requestEntityRepository;

    @Mock
    private RequestEntityController requestEntityController;

    @Mock
    private TextResponseService responseService;
    @Mock
    private RequestComponent component;

    TextRequestService textRequestService;

    @BeforeEach
    public void setUp() {
        textRequestService = new TextRequestService(requestEntityRepository, component, responseService);
    }

    @Test
    public void statusShouldBeCreated()
            throws TextRequestException, ParserException, ContextException {
        TextRequest textRequest = TextRequest.builder()
                .idUser(1234L)
                .text("How many months does a year have?")
                .build();
        TextRequestResponse textRequestResponse = TextRequestResponse.builder()
                .idUser(1234L)
                .text("A year has 12 months.")
                .build();

        ResponseEntity<TextRequestResponse> expected = new ResponseEntity<>(textRequestResponse, HttpStatus.CREATED);

        when(requestEntityController.createTextRequest(textRequest)).thenReturn(expected);

        ResponseEntity<TextRequestResponse> actualResponseEntity = requestEntityController.createTextRequest(textRequest);

        assertEquals(actualResponseEntity.getStatusCode(), HttpStatus.CREATED);
    }
}