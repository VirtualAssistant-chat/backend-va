package org.fundacionjala.virtualassistant.user.service;

import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private static final long ID_USER_ENTITY = 12L;
    private static final String ID_USER_GOOGLE = "id-google";
    private static final String SPOTIFY_TOKEN = "spotify-token";
    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        userService = new UserService(userRepo);
    }

    @Test
    void shouldReturnAnEmptyListWhenIsCalledFindAll() {
        when(userRepo.findAll()).thenReturn(List.of());

        var list = userService.findAll();
        verify(userRepo).findAll();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldReturnAnElementDTOWhenIsCalledFindAll() {
        when(userRepo.findAll()).thenReturn(List.of(
                UserEntity.builder()
                        .idUser(ID_USER_ENTITY)
                        .idGoogle(ID_USER_GOOGLE)
                        .build()
        ));

        var list = userService.findAll();
        verify(userRepo).findAll();

        assertNotNull(list);
        assertEquals(list.size(), 1);
    }

    @Test
    void shouldReturnAUserWithContextsWhenIsCalledFindByIdWithContext() throws ParserException, UserRequestException {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .build();
        when(userRepo.findByIdUser(anyLong())).thenReturn(
                Optional.of(UserEntity.builder()
                        .idUser(ID_USER_ENTITY)
                        .idGoogle(ID_USER_GOOGLE)
                        .contextEntities(List.of(ContextEntity.builder()
                                .idContext(12L)
                                .title("context")
                                .userEntity(userEntity)
                                .build()))
                        .build()));

        Optional<UserContextResponse> userContextResponse = userService.findByIdWithContext(ID_USER_ENTITY);
        verify(userRepo).findByIdUser(ID_USER_ENTITY);

        assertNotNull(userContextResponse);
        userContextResponse.ifPresent(option -> {
            assertNotNull(option);
            assertFalse(option.getContextResponses().isEmpty());
            assertEquals(option.getIdUser(), ID_USER_ENTITY);
            assertEquals(option.getIdGoogle(), ID_USER_GOOGLE);
        });
    }

    @Test
    void shouldSavedAnUserAndReturnTheUserStoredWithAnId() throws ParserException {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE)
                .spotifyToken(SPOTIFY_TOKEN)
                .build();
        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

        UserRequest userRequest = UserRequest.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE)
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        UserResponse userResponse = userService.save(userRequest);
        verify(userRepo).save(any(UserEntity.class));

        assertNotNull(userResponse);
        assertEquals(userResponse.getIdUser(), ID_USER_ENTITY);
        assertEquals(userResponse.getIdGoogle(), ID_USER_GOOGLE);
    }

    @Test
    void shouldUpdateAnUserAndReturnDataUpdated() throws UserRequestException, ParserException {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

        UserRequest userRequest = UserRequest.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        UserResponse userResponse = userService.updateSpotifyToken(ID_USER_ENTITY, userRequest);
        verify(userRepo).save(any(UserEntity.class));

        assertNotNull(userResponse);
        assertEquals(userResponse.getIdUser(), ID_USER_ENTITY);
        assertEquals(userResponse.getIdGoogle(), ID_USER_GOOGLE + "-updated");
    }

    @Test
    void shouldUpdateAnUserAndDoesNotExistThrowUserRequestException() {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();
        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

        UserRequest userRequest = UserRequest.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        assertThrows(UserRequestException.class, () -> userService.updateSpotifyToken(ID_USER_ENTITY, userRequest));
    }

    @Test
    void shouldThrowParserExceptionForAnUserRequestAsNull() {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE)
                .spotifyToken(SPOTIFY_TOKEN)
                .build();
        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

        assertThrows(ParserException.class, () -> userService.save(null));
    }

    @Test
    void shouldThrowParserExceptionForAnUserEntityAsNull() {
        when(userRepo.save(any(UserEntity.class))).thenReturn(null);

        UserRequest userRequest = UserRequest.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        assertThrows(ParserException.class, () -> userService.save(userRequest));
    }

    @Test
    void shouldThrowAnParserExceptionForAnUserUpdated() {
        UserEntity userEntity = UserEntity.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userRepo.save(any(UserEntity.class))).thenReturn(null);

        UserRequest userRequest = UserRequest.builder()
                .idUser(ID_USER_ENTITY)
                .idGoogle(ID_USER_GOOGLE + "-updated")
                .spotifyToken(SPOTIFY_TOKEN)
                .build();

        assertThrows(ParserException.class, () -> userService.updateSpotifyToken(ID_USER_ENTITY, userRequest));
    }
}