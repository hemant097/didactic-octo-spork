package org.novice.learnreactprog.controller;

import lombok.RequiredArgsConstructor;
import org.novice.learnreactprog.dto.UserRequest;
import org.novice.learnreactprog.dto.UserResponse;
import org.novice.learnreactprog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable(value = "id") Long userId){
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                ;
    }

    @GetMapping
    public Flux<UserResponse> allUsers(){
        return userService.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest request){
        return userService.createUser(request)
                .map(userResponse -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(userResponse));
    }

    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserResponse> streamUsers(){
        return userService.findAll()
                .delayElements(Duration.ofMillis(500));
    }
}
