package org.fundacionjala.virtualassistant.textrequest.service;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.clients.openai.component.RequestComponent;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextRequestServiceTest {
    @Mock
    private RequestEntityRepository requestEntityRepository;

    @Mock
    private RequestComponent component;

    @Mock
    private TextResponseService responseService;

    private TextRequestService textRequestService;

    @BeforeEach
    public void setup() {
        requestEntityRepository = mock(RequestEntityRepository.class);
        textRequestService = new TextRequestService(requestEntityRepository, component, responseService);
    }

    @Test
    public void shouldCreateATextRequest()
            throws TextRequestException, ParserException {
        long idUser = 12343L;
        TextRequest textRequest = TextRequest.builder()
                .text("hello world")
                .build();
        RequestEntity requestEntity = RequestEntity.builder()
                .idUser(idUser)
                .contextEntity(ContextEntity.builder().idContext(12L).build())
                .responseEntity(ResponseEntity.builder().idResponse(12L).build())
                .build();
        try (MockedStatic<TextRequestParser> mockedStatic = mockStatic(TextRequestParser.class)) {
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(TextRequest.class)))
                    .thenReturn(requestEntity);
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(RequestEntity.class)))
                    .thenReturn(TextRequestResponse.builder().build());
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(RequestEntity.class), any(TextResponse.class)))
                    .thenReturn(TextRequestResponse.builder().build());

            when(requestEntityRepository.save(any(RequestEntity.class)))
                    .thenReturn(requestEntity);
            when(responseService.save(any(ParameterResponse.class)))
                    .thenReturn(TextResponse.builder().build());

            TextRequestResponse actualTextRequestResponse = textRequestService.save(textRequest);
            verify(requestEntityRepository).save(requestEntity);
            assertNotNull(actualTextRequestResponse);
        }
    }

    @Test
    public void shouldCreateATextRequestWithCorrectValues()
            throws TextRequestException, ParserException {
        final long idUser = 12343L;
        final String text = "How many months does a year have?";
        final String textResponse = "How many months does a year have?";
        final long idContext = 1234L;

        TextRequest textRequest = TextRequest.builder()
                .idUser(idUser)
                .text(text)
                .context(ContextResponse.builder().idContext(idContext).build())
                .build();

        RequestEntity requestEntity = RequestEntity.builder()
                .idUser(idUser)
                .text(text)
                .contextEntity(ContextEntity.builder().idContext(idContext).build())
                .build();

        try (MockedStatic<TextRequestParser> mockedStatic = mockStatic(TextRequestParser.class)) {
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(TextRequest.class)))
                    .thenReturn(requestEntity);
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(RequestEntity.class)))
                    .thenReturn(TextRequestResponse.builder()
                            .idUser(idUser)
                            .text(textResponse)
                            .idContext(idContext)
                            .build());
            mockedStatic.when(() -> TextRequestParser.parseFrom(any(RequestEntity.class), any(TextResponse.class)))
                    .thenReturn(TextRequestResponse.builder()
                            .idUser(idUser)
                            .text(textResponse)
                            .idContext(idContext)
                            .build());
            when(requestEntityRepository.save(any(RequestEntity.class)))
                    .thenReturn(requestEntity);
            when(responseService.save(any(ParameterResponse.class)))
                    .thenReturn(TextResponse.builder().build());

            TextRequestResponse actualTextRequestResponse = textRequestService.save(textRequest);

            verify(requestEntityRepository).save(requestEntity);
            assertNotNull(actualTextRequestResponse);

            assertEquals(actualTextRequestResponse.getIdUser(), idUser);
            assertEquals(actualTextRequestResponse.getText(), textResponse);
            assertEquals(actualTextRequestResponse.getIdContext(), idContext);
        }
    }

    @Test
    public void shouldThrowsAnException() {
        RequestEntity requestEntity = RequestEntity.builder().idUser(null).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        requestEntityRepository.save(requestEntity);

        assertThrows(TextRequestException.class, () -> textRequestService.save(null));
    }

    @Test
    public void shouldThrowsACorrectExceptionMessage() {
        RequestEntity requestEntity = RequestEntity.builder().idUser(null).build();

        when(requestEntityRepository.save(any(RequestEntity.class)))
                .thenReturn(requestEntity);

        requestEntityRepository.save(requestEntity);

        Exception exceptionExpected = assertThrows(TextRequestException.class, () -> textRequestService.save(null));

        String expected = "User id should not be null";
        String actual = exceptionExpected.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowParserExceptionForSaveATextRequest() {
        TextRequest textRequest = TextRequest.builder()
                .text("hello world")
                .build();
        assertThrows(ParserException.class, () -> textRequestService.save(textRequest));
    }
}
