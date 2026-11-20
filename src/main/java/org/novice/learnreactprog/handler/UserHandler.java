package org.novice.learnreactprog.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.novice.learnreactprog.dto.UserRequest;
import org.novice.learnreactprog.dto.UserResponse;
import org.novice.learnreactprog.service.UserService;
import org.novice.learnreactprog.validator.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final RequestValidator validator;

    public Mono<ServerResponse> getAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAll(), UserResponse.class);
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(UserRequest.class)
                .flatMap( userReq -> validator.validate(userReq))
                .flatMap(userReq -> userService.createUser(userReq))
                .flatMap(createdUser -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdUser));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {

        Long userId = parseId(request);

        return userService.getUserById(userId)
                .flatMap(userResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

    private Long parseId(ServerRequest request){
        try{
            return Long.parseLong(request.pathVariable("id"));
        }catch (NumberFormatException nfe){
            throw new IllegalArgumentException("Invalid id format");
        }
    }
}
