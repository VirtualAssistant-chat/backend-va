package org.fundacionjala.virtualassistant.context.controller;

import lombok.NonNull;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.exception.ContextRequestException;
import org.fundacionjala.virtualassistant.context.service.ContextService;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/context")
public class ContextController {
    private ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ContextResponse>> getContextByUser(@NonNull @PathVariable("idUser") Long idUser)
            throws ContextException {
        Optional<List<ContextResponse>> optionalContexts = Optional
                .ofNullable(contextService.findContextByUserId(idUser));
        return optionalContexts.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{idContext}")
    public ResponseEntity<ContextResponse> findById(@PathVariable Long idContext)
            throws ContextRequestException, ParserException {
        Optional<ContextResponse> contextResponse = contextService.findById(idContext);
        return contextResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ContextResponse> saveContextByUser(@Valid @RequestBody ContextRequest request)
            throws ContextException, ParserException {
        Optional<ContextResponse> context = Optional.ofNullable(contextService.saveContext(request));
        return context.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{idContext}")
    public ResponseEntity<ContextResponse> putContext(@NonNull @PathVariable("idContext") Long idContext,
                                                      @Valid @RequestBody ContextRequest request)
            throws ContextException, ParserException {
        Optional<ContextResponse> context = Optional.ofNullable(contextService.editContext(idContext, request));
        return context.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(()-> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{idContext}")
    public ResponseEntity<Boolean> deleteContextById(@NonNull @PathVariable("idContext") Long idContext) {
        try {
            boolean isDelete = contextService.deleteContext(idContext);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(isDelete);
        }catch (ContextException e){
            return ResponseEntity.notFound().build();
        }
    }
}