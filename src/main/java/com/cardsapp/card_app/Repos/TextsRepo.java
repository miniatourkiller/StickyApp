package com.cardsapp.card_app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardsapp.card_app.Entities.TextsEntity;

public interface TextsRepo extends JpaRepository<TextsEntity, Long> {
    
}
