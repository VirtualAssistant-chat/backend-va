package org.fundacionjala.virtualassistant.request.controller;

import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textrequest.controller.RequestEntityController;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextRequestControllerTest {
    private RequestEntityController textRequestController;
    private static final Long CONTEXT_ID_1 = 1L;
    private static final Long USER_ID_1 = 2L;
    private static final Long CONTEXT_ID_2 = 2L;
    private static final Long USER_ID_2 = 1L;
    private RequestEntity requestA;
    private RequestEntity requestB;
    List<RequestEntity> requests;
    private List<TextRequestResponse> requestResponses;

    @BeforeEach
    void setUp() {
        textRequestController = mock(RequestEntityController.class);
        requestA = new RequestEntity();
        requestB = new RequestEntity();

        Either<Exception, TextRequestResponse> either = new Either<>();
        requests = new ArrayList<>();
        requestResponses =
                requests.stream()
                        .map(either.lift(requestEntity -> {
                            try {
                                return Either.right(TextRequestParser.parseFrom(requestEntity));
                            } catch (ParserException e) {
                                return Either.left(e);
                            }
                        }))
                        .filter(Either::isRight)
                        .map(Either::getRight)
                        .collect(Collectors.toList());
    }

    @Test
    void getTextRequestsById() {
        List<RequestEntity> requests = new ArrayList<>();
        requests.add(RequestEntity.builder()
                .idRequest(2L)
                .contextEntity(ContextEntity.builder().idContext(CONTEXT_ID_1).build())
                .responseEntity(
                        org.fundacionjala.virtualassistant.models.ResponseEntity.builder()
                                .build()
                )
                .idUser(USER_ID_1)
                .build());
        requests.add(RequestEntity.builder()
                .idRequest(2L)
                .contextEntity(ContextEntity.builder().idContext(CONTEXT_ID_2).build())
                .responseEntity(org.fundacionjala.virtualassistant.models.ResponseEntity.builder()
                        .build())
                .idUser(USER_ID_2)
                .build());


        when(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1))
                .thenReturn(ResponseEntity.ok(requestResponses));

        assertEquals(textRequestController.getTextRequests(USER_ID_1, CONTEXT_ID_1).getBody(), requestResponses);
    }

    @Test
    void getTextRequestsByIdWithAnotherValuesOfId() {
        requests.add(RequestEntity.builder()
                .idRequest(2L)
                .contextEntity(ContextEntity.builder().idContext(CONTEXT_ID_1).build())
                .responseEntity(
                        org.fundacionjala.virtualassistant.models.ResponseEntity.builder()
                                .build()
                )
                .idUser(USER_ID_1)
                .build());
        requests.add(RequestEntity.builder()
                .idRequest(2L)
                .contextEntity(ContextEntity.builder().idContext(CONTEXT_ID_2).build())
                .responseEntity(
                        org.fundacionjala.virtualassistant.models.ResponseEntity.builder()
                                .build()
                )
                .idUser(USER_ID_2)
                .build());

        when(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2))
                .thenReturn(ResponseEntity.ok(requestResponses));

        assertEquals(textRequestController.getTextRequests(USER_ID_2, CONTEXT_ID_2).getBody(), requestResponses);
    }
}