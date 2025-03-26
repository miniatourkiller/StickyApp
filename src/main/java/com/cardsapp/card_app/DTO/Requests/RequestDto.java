package com.cardsapp.card_app.DTO.Requests;

import lombok.Data;

@Data
public abstract class RequestDto {
    private int pageSize;
    private int pageNumber;
}
