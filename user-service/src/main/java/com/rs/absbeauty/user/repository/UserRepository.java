package com.rs.absbeauty.user.repository;

import com.rs.absbeauty.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


/**
 * created by rs 7/28/2025.
 */
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Mono<User> findByEmail(String email);
    Mono<User> findByUsername(String username);
}
