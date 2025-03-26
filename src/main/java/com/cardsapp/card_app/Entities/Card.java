package com.cardsapp.card_app.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Card extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private String title;
    @OneToMany
    private List<TextsEntity> texts;
    private boolean reminder;
    private int reminderCount;
    private String reminderTime;
    @ManyToOne
    private UserEntity owner;
}