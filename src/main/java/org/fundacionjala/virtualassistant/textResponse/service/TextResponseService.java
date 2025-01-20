package org.fundacionjala.virtualassistant.textResponse.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.models.RequestEntity;
import org.fundacionjala.virtualassistant.models.ResponseEntity;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.parser.ResponseParser;
import org.fundacionjala.virtualassistant.textResponse.repository.ResponseEntityRepository;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.util.either.Either;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TextResponseService {

    private ResponseEntityRepository repository;
    private static final Either<Exception, TextResponse> either = new Either<>();

    public List<TextResponse> findAll() {
        return repository.findAll().stream()
                .map(either.lift(responseEntity -> {
                    try {
                        return Either.right(ResponseParser.parseFrom(responseEntity));
                    } catch (ParserException e) {
                        return Either.left(e);
                    }
                }))
                .filter(Either::isRight)
                .map(Either::getRight)
                .collect(Collectors.toList());
    }

    public TextResponse save(long idRequest, String text) throws ParserException {
        ResponseEntity responseEntity = ResponseEntity.builder()
                .requestEntity(RequestEntity.builder()
                        .idRequest(idRequest).build())
                .text(text)
                .date(ZonedDateTime.now())
                .build();
        ResponseEntity responseEntitySaved = repository.save(responseEntity);
        return ResponseParser.parseFrom(responseEntitySaved);
    }

    public TextResponse save(ParameterResponse parameterResponse) throws ParserException {
        ResponseEntity responseEntity = ResponseParser.parseFrom(parameterResponse);
        ResponseEntity responseEntitySaved = repository.save(responseEntity);
        return ResponseParser.parseFrom(responseEntitySaved);
    }

}
