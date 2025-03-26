package com.cardsapp.card_app.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardsapp.card_app.Entities.Card;
import com.cardsapp.card_app.Entities.UserEntity;

public interface CardRepo extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.reminder = ?1 AND c.reminderTime <= ?2 AND c.reminderCount < 3")
    public List<Card> findByReminderAndReminderTimeReached(boolean reminder, String currentDateTime);

    public List<Card> findByOwnerAndDeletedFalse(UserEntity owner);
}
