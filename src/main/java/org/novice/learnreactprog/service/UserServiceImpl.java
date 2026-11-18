package org.novice.learnreactprog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.novice.learnreactprog.dto.UserRequest;
import org.novice.learnreactprog.dto.UserResponse;
import org.novice.learnreactprog.entity.User;
import org.novice.learnreactprog.exception.ResourceNotFoundException;
import org.novice.learnreactprog.repo.UserRepository;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
@EnableR2dbcAuditing
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public Mono<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("user",id)))
                .map(user ->  UserResponse.from(user))
                .doOnSuccess((userResp)->log.info("user response received successfully with id: {}, at time: {}",userResp.getId(), LocalDateTime.now()));
    }

    @Override
    public Flux<UserResponse> findAll() {
        return userRepository.findAll()
                .map(UserResponse::from);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest request) {
        return userRepository
                .existsByEmail(request.getEmail())
                .flatMap(exists -> {
                    if (exists)
                        return Mono.error(new IllegalArgumentException("user already exists with email"));

                    User user = new User();
                    user.setName(request.getName());
                    user.setEmail(request.getEmail());
                    user.setRole(request.getRole());

                    return userRepository.save(user);
                })
                .map(savedUser -> UserResponse.from(savedUser))
                .doOnSuccess(u-> log.info("created user with id: {}",u.getId()));

    }
}
