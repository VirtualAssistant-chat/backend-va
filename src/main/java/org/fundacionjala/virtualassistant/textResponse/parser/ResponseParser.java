package org.fundacionjala.virtualassistant.textResponse.parser;

import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.exception.TextResponseParserException;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

public class ResponseParser {
    public static TextResponse parseFrom(ResponseEntity responseEntity) throws ParserException {
        verifyResponseEntity(responseEntity);
        return TextResponse.builder()
                .idRequest(responseEntity.getRequestEntity().getIdRequest())
                .idResponse(responseEntity.getIdResponse())
                .text(responseEntity.getText())
                .date(responseEntity.getDate())
                .build();
    }

    public static TextResponse parseWithOutIdFrom(ResponseEntity responseEntity) throws ParserException {
        verifyResponseEntity(responseEntity);
        return TextResponse.builder()
                .idResponse(responseEntity.getIdResponse())
                .text(responseEntity.getText())
                .date(responseEntity.getDate())
                .build();
    }

    public static ResponseEntity parseFrom(ParameterResponse parameterResponse) throws ParserException {
        if (isNull(parameterResponse)) {
            throw new TextResponseParserException(TextResponseParserException.MESSAGE_PARAMETER_RESPONSE);
        }
        return ResponseEntity.builder()
                .text(parameterResponse.getText())
                .date(ZonedDateTime.now())
                .requestEntity(parseFrom(parameterResponse.getRequest()))
                .build();
    }

    public static RequestEntity parseFrom(TextRequestResponse textRequestResponse) throws ParserException {
        if (isNull(textRequestResponse)) {
            throw new TextResponseParserException(TextResponseParserException.MESSAGE_PARAMETER_RESPONSE);
        }
        return RequestEntity.builder()
                .idRequest(textRequestResponse.getIdRequest())
                .build();
    }

    private static void verifyResponseEntity(ResponseEntity responseEntity) throws TextResponseParserException {
        if (isNull(responseEntity)) {
            throw new TextResponseParserException(TextResponseParserException.MESSAGE_RESPONSE_ENTITY);
        }
    }

}
