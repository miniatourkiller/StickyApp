package com.cardsapp.card_app.Services;

import com.cardsapp.card_app.DTO.CardDto;
import com.cardsapp.card_app.DTO.LoginDto;
import com.cardsapp.card_app.DTO.ResponseDto;
import com.cardsapp.card_app.DTO.TextDto;
import com.cardsapp.card_app.DTO.UserDto;
import com.cardsapp.card_app.DTO.Requests.RequestCard;
import com.cardsapp.card_app.DTO.Requests.RequestUsers;

public interface AllServices {
    public ResponseDto addCard(CardDto cardDto);
    public ResponseDto deleteCard(Long cardId);
    public ResponseDto updateCard(CardDto cardDto);

    public ResponseDto addText(Long cardId, TextDto textDto);
    public ResponseDto deleteText(Long textId);
    public ResponseDto updateText(TextDto textDto);
    
    public ResponseDto underline(Long textId, boolean status);
    public ResponseDto highlight(Long textId, boolean status);
    public ResponseDto lineThrough(Long textId, boolean status);

    public ResponseDto registerUser(UserDto userDto);
    public ResponseDto adminCreateAdmin(UserDto userDto);
    public ResponseDto loginUser(LoginDto login);

    public ResponseDto userActivation(String email, boolean status);
    public ResponseDto userDeletion(String userEmail);

    public ResponseDto getCards(RequestCard requestCard);
    public ResponseDto getUsers(RequestUsers requestUsers);
}
