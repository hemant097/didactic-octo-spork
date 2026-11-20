package org.novice.learnreactprog.repo;

import org.novice.learnreactprog.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> ,
        ReactiveSortingRepository<User,Long> {

    Mono<Boolean> existsByEmail(String email);
}
