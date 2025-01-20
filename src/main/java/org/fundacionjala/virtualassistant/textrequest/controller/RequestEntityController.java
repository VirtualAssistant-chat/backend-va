package org.fundacionjala.virtualassistant.textrequest.controller;

import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/textRequest")
public class RequestEntityController {
    @Autowired
    TextRequestService requestEntityService;

    @PostMapping
    public ResponseEntity<TextRequestResponse> createTextRequest(@Valid @RequestBody TextRequest textRequest)
            throws TextRequestException, ParserException {
        TextRequestResponse textRequestResponse = requestEntityService.save(textRequest);
        return new ResponseEntity<>(textRequestResponse, CREATED);
    }

    @GetMapping("/{userId}/context/{contextId}")
    public ResponseEntity<List<TextRequestResponse>> getTextRequests(@NotNull @PathVariable Long userId,
                                                                     @NotNull @PathVariable Long contextId) {
        List<TextRequestResponse> textRequests = requestEntityService.getTextRequestByUserAndContext(userId, contextId);
        return new ResponseEntity<>(textRequests, OK);
    }
}