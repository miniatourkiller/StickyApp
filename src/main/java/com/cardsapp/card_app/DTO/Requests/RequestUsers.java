package com.cardsapp.card_app.DTO.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUsers extends RequestDto {
    private String fullName;
    private String email;
    private String dob;
}
