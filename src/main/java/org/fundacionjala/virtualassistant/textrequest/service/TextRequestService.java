package org.fundacionjala.virtualassistant.textrequest.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;

import javax.validation.Valid;

import org.fundacionjala.virtualassistant.clients.openai.component.RequestComponent;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.repository.RequestEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.parser.TextRequestParser;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.fundacionjala.virtualassistant.util.string.StringSolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class TextRequestService {
    private static final Either<Exception, TextRequestResponse> either = new Either<>();
    private static final String TEXT_REQUEST_USER_ID_NULL = "User id should not be null";
    private RequestEntityRepository requestEntityRepository;
    private RequestComponent requestComponent;
    private TextResponseService responseService;

    public TextRequestResponse save(@Valid TextRequest textRequest)
            throws TextRequestException, ParserException {
        if (isNull(textRequest)) {
            throw new TextRequestException(TEXT_REQUEST_USER_ID_NULL);
        }
        String text = StringSolver.deleteTabAndLineBreak(textRequest.getText());
        return save(textRequest, requestComponent.getResponse(text));
    }

    public TextRequestResponse save(@Valid TextRequest textRequest, String response)
            throws TextRequestException, ParserException {
        RequestEntity requestEntity = TextRequestParser.parseFrom(textRequest);
        RequestEntity savedRequestEntity = requestEntityRepository.save(requestEntity);
        TextResponse textResponse = responseService.save(ParameterResponse.builder()
                .text(response)
                .request(TextRequestResponse.builder()
                        .idRequest(savedRequestEntity.getIdRequest())
                        .build())
                .build());

        return TextRequestParser.parseFrom(savedRequestEntity, textResponse);
    }

    public List<TextRequestResponse> getTextRequestByUserAndContext(Long id, Long contextId) {
        return requestEntityRepository.findAllByIdUserAndContextEntityIdContext(id, contextId).stream()
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
}