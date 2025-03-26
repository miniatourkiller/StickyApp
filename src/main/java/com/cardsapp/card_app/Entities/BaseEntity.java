package com.cardsapp.card_app.Entities;

import com.cardsapp.card_app.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Column(nullable = false, updatable = false)
    private String dateCreated = DateUtils.currentDate();
    @Column(insertable = false)
    private String dateUpdated = DateUtils.currentDate();
    private boolean deleted = false;
}
