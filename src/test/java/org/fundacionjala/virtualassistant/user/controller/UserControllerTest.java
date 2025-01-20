package org.fundacionjala.virtualassistant.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.error.ErrorResponse;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    final Long ID_USER = 1L;
    final String ID_GOOGLE = "1";
    final String SPOTIFY_TOKEN = "token";
    final String PARSER_EXCEPTION = "ParserException";
    final String USER_REQUEST_EXCEPTION = "UserRequestException";

    UserRequest userRequest;

    UserResponse userResponse;

    UserContextResponse userContextResponse;

    @BeforeEach
    public void setUp() {
        List<ContextResponse> contextResponses = new ArrayList<>();
        userContextResponse = UserContextResponse.builder()
                .contextResponses(contextResponses)
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();

        userRequest = UserRequest.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        userResponse = UserResponse.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();
    }

    @Test
    public void shouldSaveAnUser() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);

        assertNotNull(resultUserEntity);
        assertEquals(CREATED, resultUserEntity.getStatusCode());
    }

    @Test
    public void shouldUpdateSpoityToken() throws ParserException, UserRequestException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        UserRequest updatedUserToken = UserRequest.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .spotifyToken("tokenUpdated")
                .build();

        UserResponse updatedUserTokenResponse = UserResponse.builder()
                .idUser(ID_USER)
                .idGoogle(ID_GOOGLE)
                .build();

        when(userService.updateSpotifyToken(1L, updatedUserToken)).thenReturn(updatedUserTokenResponse);

        ResponseEntity<UserResponse> resultUserEntity2 = userController.updateSpotifyToken(ID_USER, updatedUserToken);
        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
    }

    @Test
    public void shouldFoundUserById() throws ParserException, UserRequestException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        when(userService.findById(ID_USER)).thenReturn(Optional.of(userResponse));
        ResponseEntity<UserResponse> resultUserEntity2 = userController.findById(ID_USER);

        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
        assertEquals(ID_USER, resultUserEntity2.getBody().getIdUser());
        assertEquals(ID_GOOGLE, resultUserEntity2.getBody().getIdGoogle());
    }

    @Test
    public void listGetHaveSizeOne() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        List<UserResponse> userResponseList = Arrays.asList(userResponse);

        when(userService.findAll()).thenReturn(userResponseList);

        ResponseEntity<List<UserResponse>> resultList = userController.findAll();
        assertNotNull(resultList);
        assertEquals(1, resultList.getBody().size());
    }

    @Test
    public void shouldFoundContextUserById() throws ParserException, UserRequestException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        when(userService.findByIdWithContext(ID_USER)).thenReturn(Optional.of(userContextResponse));
        ResponseEntity<UserContextResponse> resultUserEntity2 = userController.findByIdWithContext(ID_USER);

        assertNotNull(resultUserEntity2);
        assertEquals(OK, resultUserEntity2.getStatusCode());
        assertEquals(ID_USER, Objects.requireNonNull(resultUserEntity2.getBody()).getIdUser());
        assertEquals(ID_GOOGLE, resultUserEntity2.getBody().getIdGoogle());
    }

    @Test
    public void listGetWithContextHaveSizeOne() throws ParserException {
        when(userService.save(userRequest)).thenReturn(userResponse);
        ResponseEntity<UserResponse> resultUserEntity = userController.createUser(userRequest);
        assertNotNull(resultUserEntity);

        List<UserContextResponse> userResponseList = Arrays.asList(userContextResponse);

        when(userService.findAllWithContext()).thenReturn(userResponseList);

        ResponseEntity<List<UserContextResponse>> resultList = userController.findAllWithContext();
        assertNotNull(resultList);
        assertEquals(1, Objects.requireNonNull(resultList.getBody()).size());
    }

    @Test
    public void shouldGetSpotifyToken() throws UserRequestException, ParserException {
        when(userService.getSpotifyToken(ID_USER)).thenReturn(SPOTIFY_TOKEN);

        ResponseEntity<String> result = userController.getSpotifyToken(ID_USER);

        assertNotNull(result);
        assertEquals(OK, result.getStatusCode());
        assertEquals(SPOTIFY_TOKEN, result.getBody());
    }

    @DisplayName("Throw Exception with status")
    @ParameterizedTest(name = "{index} : {argumentsWithNames}")
    @MethodSource("handleExceptionAndAssert")
    void shouldBeAnErrorResponseByHandleExceptionExceptionParserExceptionAndUserRequestException(Exception e,
                                                                                                 HttpStatus expected) {
        String nameException = e.getClass().getSimpleName();
        ResponseEntity<ErrorResponse> responseEntity = userController.handleException(e);
        if (nameException.contains(PARSER_EXCEPTION)) {
            responseEntity = userController.handleException(new ParserException(e.getMessage()));
        } else if (nameException.contains(USER_REQUEST_EXCEPTION)) {
            responseEntity = userController.handleException(new UserRequestException(e.getMessage()));
        }

        assertNotNull(responseEntity);
        assertEquals(expected, responseEntity.getStatusCode());
    }

    private static Stream<Arguments> handleExceptionAndAssert() {
        return Stream.of(
                Arguments.of(new Exception("message"), HttpStatus.BAD_REQUEST),
                Arguments.of(new ParserException("message"), HttpStatus.BAD_REQUEST),
                Arguments.of(new UserRequestException("message"), HttpStatus.NOT_FOUND)
        );
    }
}
