package org.fundacionjala.virtualassistant.textResponse.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.textResponse.response.ParameterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;
import org.fundacionjala.virtualassistant.textResponse.service.TextResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/textResponse")
public class ResponseController {
    TextResponseService responseService;

    public ResponseController(TextResponseService textResponseService) {
        this.responseService = textResponseService;
    }

    @PostMapping
    public ResponseEntity<TextResponse> createTextResponse(@Valid @RequestBody ParameterResponse response)
            throws ParserException {
        TextResponse textResponse = responseService.save(response);
        return new ResponseEntity<>(textResponse, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TextResponse>> findAll() {
        return new ResponseEntity<>(responseService.findAll(), HttpStatus.OK);
    }
}
