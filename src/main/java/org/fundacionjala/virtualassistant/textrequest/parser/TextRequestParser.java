package org.fundacionjala.virtualassistant.textrequest.parser;

import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.parser.ContextParser;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.parser.ResponseParser;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestParserException;

import static java.util.Objects.isNull;

public class TextRequestParser {
    public static RequestEntity parseFrom(TextRequest textRequest)
            throws ParserException {
        verifyTextRequest(textRequest);
        return RequestEntity.builder()
                .idUser(textRequest.getIdUser())
                .text(textRequest.getText())
                .idAudioMongo(textRequest.getIdAudioMongo())
                .contextEntity(ContextParser.parseFrom(textRequest.getContext()))
                .date(textRequest.getDate())
                .build();
    }

    public static TextRequestResponse parseFrom(RequestEntity requestEntity)
            throws ParserException {
        verifyRequestEntity(requestEntity);
        return TextRequestResponse.builder()
                .idRequest(requestEntity.getIdRequest())
                .idUser(requestEntity.getIdUser())
                .idContext(requestEntity.getContextEntity().getIdContext())
                .text(requestEntity.getText())
                .date(requestEntity.getDate())
                .textResponse(ResponseParser.parseWithOutIdFrom(requestEntity.getResponseEntity()))
                .build();
    }

    public static TextRequestResponse parseFrom(RequestEntity requestEntity, TextResponse textResponse)
            throws ParserException {
        verifyRequestEntity(requestEntity);
        if (isNull(requestEntity.getContextEntity())) {
            throw new TextRequestParserException(ContextException.MESSAGE_CONTEXT_NULL);
        }
        return TextRequestResponse.builder()
                .idRequest(requestEntity.getIdRequest())
                .idUser(requestEntity.getIdUser())
                .idContext(requestEntity.getContextEntity().getIdContext())
                .text(requestEntity.getText())
                .date(requestEntity.getDate())
                .textResponse(textResponse)
                .build();
    }

    private static void verifyTextRequest(TextRequest textRequest) throws TextRequestParserException {
        if (isNull(textRequest)) {
            throw new TextRequestParserException(TextRequestParserException.MESSAGE_TEXT_REQUEST);
        }
    }

    private static void verifyRequestEntity(RequestEntity requestEntity) throws TextRequestParserException {
        if (isNull(requestEntity)) {
            throw new TextRequestParserException(TextRequestParserException.MESSAGE_TEXT_REQUEST_ENTITY);
        }
    }
}
