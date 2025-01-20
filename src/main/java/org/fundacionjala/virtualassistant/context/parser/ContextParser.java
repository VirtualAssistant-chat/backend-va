package org.fundacionjala.virtualassistant.context.parser;

import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextParserException;
import org.fundacionjala.virtualassistant.context.models.ContextEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.fundacionjala.virtualassistant.user.controller.parser.UserParser;
import org.fundacionjala.virtualassistant.user.exception.UserParserException;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ContextParser {
    private static final Either<Exception, TextRequestResponse> either =  new Either<>();

    public static ContextResponse parseFrom(ContextEntity context) throws ParserException {
        if (isNull(context)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_ENTITY);
        }
        if (isNull(context.getUserEntity())) {
            throw new UserParserException(UserParserException.MESSAGE_USER_ENTITY);
        }
        return ContextResponse.builder()
                .idContext(context.getIdContext())
                .title(context.getTitle())
                .idUser(context.getUserEntity().getIdUser())
                .build();
    }

    @NotNull
    private static List<TextRequestResponse> parseRequestEntities(ContextEntity context) {
        return context.getRequestEntities().stream()
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

    public static ContextEntity parseFrom(ContextRequest contextRequest)
            throws ParserException {
        verifyContextRequest(contextRequest);
        return ContextEntity.builder()
                .title(contextRequest.getTitle())
                .userEntity(UserParser.parseFrom(contextRequest.getUserRequest()))
                .requestEntities(new ArrayList<>())
                .build();
    }

    public static ContextEntity parseFrom(ContextResponse contextResponse) throws ContextParserException {
        if (isNull(contextResponse)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_RESPONSE);
        }
        return ContextEntity.builder()
                .idContext(contextResponse.getIdContext())
                .build();
    }

    public static ContextEntity parseFrom(Long idContext, ContextRequest contextRequest)
            throws ParserException {
        verifyContextRequest(contextRequest);
        return ContextEntity.builder()
                .title(contextRequest.getTitle())
                .idContext(idContext)
                .userEntity(UserParser.parseFrom(contextRequest.getUserRequest()))
                .build();
    }

    private static void verifyContextRequest(ContextRequest contextRequest) throws ParserException {
        if (isNull(contextRequest)) {
            throw new ContextParserException(ContextParserException.MESSAGE_CONTEXT_REQUEST);
        }
    }
}
