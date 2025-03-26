package com.cardsapp.card_app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardsapp.card_app.Entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    
}
