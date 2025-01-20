package org.fundacionjala.virtualassistant.user.controller.parser;

import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextParserException;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.models.UserEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserParserException;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.either.ProcessorEither;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class UserParser {

    private static final ProcessorEither<Exception, ContextResponse> either = new Either<>();

    public static UserResponse parseFrom(UserEntity userEntity) throws ParserException {
        verifyUserEntity(userEntity);
        return UserResponse.builder()
                .idUser(userEntity.getIdUser())
                .idGoogle(userEntity.getIdGoogle())
                .spotifyToken(userEntity.getSpotifyToken())
                .build();
    }

    public static UserContextResponse parseFromWithContext(UserEntity userEntity)
            throws ParserException {
        verifyUserEntity(userEntity);
        if (isNull(userEntity.getContextEntities())) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_ENTITY);
        }
        return UserContextResponse.builder()
                .idUser(userEntity.getIdUser())
                .idGoogle(userEntity.getIdGoogle())
                .contextResponses(parseFrom(userEntity.getContextEntities()))
                .build();
    }

    public static List<ContextResponse> parseFrom(List<ContextEntity> contextEntities) {
        return getContextResponses(contextEntities);
    }

    @NotNull
    public static List<ContextResponse> getContextResponses(List<ContextEntity> contextEntities) {
        return contextEntities.stream()
                .map(
                        either.lift(contextEntity -> {
                            try {
                                return Either.right(ContextParser.parseFrom(contextEntity));
                            } catch (ParserException e) {
                                return Either.left(e);
                            }
                        })
                )
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    public static UserEntity parseFrom(UserRequest userRequest) throws ParserException {
        if (isNull(userRequest)) {
            throw new UserParserException(UserParserException.MESSAGE_USER_REQUEST);
        }
        return UserEntity.builder()
                .idUser(userRequest.getIdUser())
                .idGoogle(userRequest.getIdGoogle())
                .contextEntities(new ArrayList<>())
                .spotifyToken(userRequest.getSpotifyToken())
                .build();
    }

    private static void verifyUserEntity(UserEntity userEntity) throws ParserException {
        if (isNull(userEntity)) {
            throw new UserParserException(UserParserException.MESSAGE_USER_ENTITY);
        }
    }
}
