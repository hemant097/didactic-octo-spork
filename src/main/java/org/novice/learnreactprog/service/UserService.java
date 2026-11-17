package org.novice.learnreactprog.service;


import org.novice.learnreactprog.dto.UserRequest;
import org.novice.learnreactprog.dto.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserService {

    Mono<UserResponse> getUserById(Long id);

    Flux<UserResponse> findAll();

    Mono<UserResponse> createUser(UserRequest request);
}
