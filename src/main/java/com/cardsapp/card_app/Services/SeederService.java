package com.cardsapp.card_app.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardsapp.card_app.Entities.UserEntity;
import com.cardsapp.card_app.Enums.Roles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeederService {
    private final PasswordEncoder passwordEncoder;
    private final CacheServices cacheServices;

    public void seedUser() {
        // create default admin user
        String userEmail = "jgathiru91@gmail.com";
        UserEntity user = cacheServices.getUser(userEmail);
        if (user == null) {
            user = new UserEntity();
            user.setId(1l);
            user.setEmail(userEmail);
            user.setFullName("James Gathiru Karu");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(Roles.ADMIN);
            cacheServices.updateUser(user);
        }
    }
}
