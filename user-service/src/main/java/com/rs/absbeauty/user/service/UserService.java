package com.rs.absbeauty.user.service;

import com.rs.absbeauty.user.model.User;
import com.rs.absbeauty.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * created by rs 7/28/2025.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Mono<User> registerUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new RuntimeException("Email already exists")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            user.setPassword(passwordEncoder.encode(user.getPassword()));
                            user.setRoles(List.of("USER"));
                            user.setCreatedAt(LocalDateTime.now());
                            return userRepository.save(user);
                        })
                );
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
