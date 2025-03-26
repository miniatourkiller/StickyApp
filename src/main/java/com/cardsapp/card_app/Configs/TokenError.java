package com.cardsapp.card_app.Configs;

import com.cardsapp.card_app.DTO.ResponseDto;

public class TokenError extends RuntimeException {
    public ResponseDto responseDto;
    public TokenError(String message){
        super(message);
        responseDto = new ResponseDto(300, message);
    }
}
