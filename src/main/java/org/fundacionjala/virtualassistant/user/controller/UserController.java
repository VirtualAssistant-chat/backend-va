package org.fundacionjala.virtualassistant.user.controller;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.error.ErrorResponse;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.fundacionjala.virtualassistant.user.controller.request.UserRequest;
import org.fundacionjala.virtualassistant.user.controller.response.UserContextResponse;
import org.fundacionjala.virtualassistant.user.controller.response.UserResponse;
import org.fundacionjala.virtualassistant.user.exception.UserRequestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws ParserException {
        UserResponse userResponse = userService.save(userRequest);
        return new ResponseEntity<>(userResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateSpotifyToken(@NotNull @PathVariable Long id, @RequestBody UserRequest userRequest)
            throws UserRequestException, ParserException {
        UserResponse userResponse = userService.updateSpotifyToken(id, userRequest);
        return new ResponseEntity<>(userResponse, OK);
    }

    @GetMapping("/{id}/spotify-token")
    public ResponseEntity<String> getSpotifyToken(@PathVariable Long id) throws UserRequestException, ParserException {
        String spotifyToken = userService.getSpotifyToken(id);
        return new ResponseEntity<>(spotifyToken, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@NotNull @PathVariable Long id) throws ParserException, UserRequestException {
        return new ResponseEntity<>(userService.findById(id).get(), OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(userService.findAll(), OK);
    }

    @GetMapping("/{id}/context")
    public ResponseEntity<UserContextResponse> findByIdWithContext(@NotNull @PathVariable Long id)
            throws ParserException, UserRequestException {
        return new ResponseEntity<>(userService.findByIdWithContext(id).get(), OK);
    }

    @GetMapping("/list/context")
    public ResponseEntity<List<UserContextResponse>> findAllWithContext() {
        return new ResponseEntity<>(userService.findAllWithContext(), OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception parserException) {
        return handleErrorResponse(parserException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ParserException.class)
    public ResponseEntity<ErrorResponse> handleException(ParserException parserException) {
        return handleErrorResponse(parserException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserRequestException.class)
    public ResponseEntity<ErrorResponse> handleException(UserRequestException parserException) {
        return handleErrorResponse(parserException, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> handleErrorResponse(Exception e, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(
                        ErrorResponse.builder()
                                .message(e.getMessage())
                                .build()
                );
    }
}
