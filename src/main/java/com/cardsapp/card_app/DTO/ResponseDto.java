package com.cardsapp.card_app.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto{
    private int status;
    private String message;
    private Object data;

    public ResponseDto(int status, String message){
        this.status = status;
        this.message = message;
    }
}