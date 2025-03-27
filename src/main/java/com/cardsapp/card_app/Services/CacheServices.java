package com.cardsapp.card_app.Services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.cardsapp.card_app.Entities.UserEntity;
import com.cardsapp.card_app.Repos.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheServices {
    private final UserRepo userRepo;

    @Cacheable(value = "user", key = "#email")
    public UserEntity getUser(String email){
        log.info("Fetching user from cache: "+email);
        return userRepo.findByEmail(email);
    }

    //update user
    @CachePut(value = "user", key = "#user.email")
    public UserEntity updateUser(UserEntity user){
        return userRepo.save(user);
    }

    //clear all users
    @CacheEvict(value = "user", allEntries = true)
    public void clearCache(){
        log.info("Clearing cache");
    }
}
