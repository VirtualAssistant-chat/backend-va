package org.fundacionjala.virtualassistant.context;

import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.context.repository.ContextRepository;
import org.fundacionjala.virtualassistant.context.service.ContextService;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.repository.UserRepo;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContextServiceTest {

    private static final String CONTEXT_TITLE = "New Title";
    private static final long CONTEXT_USER_ID = 1L;

    ContextRequest request;
    UserEntity userEntity;

    @Mock
    private ContextRepository contextRepository;
    @Mock
    private UserRepo userRepo;

    @Mock
    private ProcessorEither<Exception, ContextResponse> processorEither;

    @InjectMocks
    private ContextService contextService;

    @BeforeEach
    public void setUp() {
        request = new ContextRequest(CONTEXT_TITLE, new UserRequest(CONTEXT_USER_ID, "string", ""));
        userEntity = new UserEntity(1L, "string", new ArrayList<>(), "");
    }

    @Test
    public void givenUserId_whenFindContextByUserId_thenContextResponsesReturned() throws ContextException {
        when(userRepo.findByIdUser(anyLong())).thenReturn(Optional.of(userEntity));
        when(contextRepository.findByUserEntityIdUser(anyLong())).thenReturn(List.of(ContextEntity.builder()
                .title(CONTEXT_TITLE)
                .userEntity(userEntity)
                .build()));

        List<ContextResponse> result = contextService.findContextByUserId(CONTEXT_USER_ID);
        int expectedResult = 1;
        int userIndex = 0;

        assertNotNull(result);
        assertEquals(expectedResult, result.size());
        assertEquals(CONTEXT_TITLE, result.get(userIndex).getTitle());
    }

    @Test
    public void givenValidContextRequest_whenSaveContext_thenContextResponseReturned()
            throws ContextException, ParserException {
        when(contextRepository.save(any(ContextEntity.class)))
                .thenReturn(new ContextEntity(2L, request.getTitle(), userEntity, new ArrayList<>()));
        when(userRepo.findByIdUser(anyLong())).thenReturn(Optional.of(userEntity));

        ContextResponse result = contextService.saveContext(request);

        assertNotNull(result);
        assertEquals(CONTEXT_TITLE, result.getTitle());
        assertEquals(CONTEXT_USER_ID, result.getIdUser());
    }

    @Test
    public void givenUserId_whenSaveContext_thenContextResponseReturned()
            throws ContextException, ParserException {
        ContextEntity savedEntity = new ContextEntity(12L, request.getTitle(), userEntity, new ArrayList<>());

        when(contextRepository.save(any(ContextEntity.class))).thenReturn(savedEntity);
        when(userRepo.findByIdUser(anyLong())).thenReturn(Optional.of(userEntity));

        ContextResponse contextResponse = contextService.saveContext(request);

        assertNotNull(contextResponse);
        assertEquals(request.getTitle(), contextResponse.getTitle());
        assertEquals(request.getUserRequest().getIdUser(), contextResponse.getIdUser());
    }

    @Test
    public void givenNullContextRequest_whenSaveContext_thenContextExceptionThrown() {
        assertThrows(ContextException.class, () -> contextService.saveContext(null));
    }

    @Test
    void shouldThrowParserExceptionForAContextEntityNull() {
        ContextRequest contextRequest = ContextRequest.builder()
                .title(CONTEXT_TITLE)
                .userRequest(UserRequest.builder()
                        .idUser(CONTEXT_USER_ID)
                        .build())
                .build();
        when(contextRepository.save(any(ContextEntity.class))).thenReturn(null);
        when(userRepo.findByIdUser(anyLong())).thenReturn(Optional.of(userEntity));
        assertThrows(ParserException.class, () -> contextService.saveContext(contextRequest));
    }

    @Test
    void shouldThrowParserExceptionForFindAContextById() {
        when(contextRepository.findById(anyLong())).thenReturn(Optional.of(ContextEntity.builder().build()));
        assertThrows(ParserException.class, () -> contextService.findById(CONTEXT_USER_ID));
    }

    @Test
    void shouldThrowParserExceptionForUpdateAContext() {
        when(userRepo.findByIdUser(anyLong())).thenReturn(Optional.of(userEntity));
        when(contextRepository.findById(anyLong())).thenReturn(Optional.of(ContextEntity.builder().build()));
        when(contextRepository.save(any(ContextEntity.class))).thenReturn(null);
        assertThrows(ParserException.class, () -> contextService.editContext(CONTEXT_USER_ID, request));
    }
}
