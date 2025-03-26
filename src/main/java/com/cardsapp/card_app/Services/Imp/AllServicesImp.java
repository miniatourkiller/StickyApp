package com.cardsapp.card_app.Services.Imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardsapp.card_app.Configs.JWTService;
import com.cardsapp.card_app.DTO.CardDto;
import com.cardsapp.card_app.DTO.LoginDto;
import com.cardsapp.card_app.DTO.ResponseDto;
import com.cardsapp.card_app.DTO.TextDto;
import com.cardsapp.card_app.DTO.UserDto;
import com.cardsapp.card_app.DTO.Requests.RequestCard;
import com.cardsapp.card_app.DTO.Requests.RequestUsers;
import com.cardsapp.card_app.Entities.Card;
import com.cardsapp.card_app.Entities.TextsEntity;
import com.cardsapp.card_app.Entities.UserEntity;
import com.cardsapp.card_app.Enums.Roles;
import com.cardsapp.card_app.Repos.CardRepo;
import com.cardsapp.card_app.Repos.CriteriaRepo;
import com.cardsapp.card_app.Repos.TextsRepo;
import com.cardsapp.card_app.Services.AllServices;
import com.cardsapp.card_app.Services.CacheServices;
import com.cardsapp.card_app.utils.UserName;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllServicesImp implements AllServices {
    private final CacheServices cacheServices;
    private final CardRepo cardRepo;
    private final TextsRepo textsRepo;
    private final PasswordEncoder passwordEncoder;
    private final CriteriaRepo criteriaRepo;
    private final JWTService jwtService;

    @Override
    public ResponseDto addCard(CardDto cardDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        //create the card entity
        Card card = new Card();
        if(cardDto.getColor() != null && isHexColor(cardDto.getColor())){
            card.setColor(cardDto.getColor());
        }else{
            return new ResponseDto(400, "Invalid color");
        }
        card.setOwner(userEntity);
        card.setTitle(cardDto.getTitle());
        cardRepo.save(card);
        return new ResponseDto(200, "Card created successfully");
    }

    //for checking valid colors
    private boolean isHexColor(String color){
        return color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
    @Override
    public ResponseDto deleteCard(Long cardId) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardId).orElse(null);
        if(card == null){
            return new ResponseDto(400, "Card not found");
        }
        if(card.getOwner().getId() != userEntity.getId()){
            return new ResponseDto(400, "You are not the owner of this card");
        }
        card.setDeleted(true);
        cardRepo.save(card);
        return new ResponseDto(200, "Card deleted successfully");
    }

    @Override
    public ResponseDto updateCard(CardDto cardDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardDto.getId()).orElse(null);
        if(card == null){
            return new ResponseDto(400, "Card not found");
        }
        if(card.getOwner().getId() != userEntity.getId()){
            return new ResponseDto(400, "You are not the owner of this card");
        }
        if(cardDto.getColor() != null && isHexColor(cardDto.getColor())){
            card.setColor(cardDto.getColor());
        }else{
            return new ResponseDto(400, "Invalid color");
        }
        card.setTitle(cardDto.getTitle());
        cardRepo.save(card);
        return new ResponseDto(200, "Card updated successfully");
    }

    @Override
    public ResponseDto addText(Long cardId, TextDto textDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());

        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardId).orElse(null);
        if(card == null){
            return new ResponseDto(400, "Card not found");
        }
        if(card.getOwner().getId() != userEntity.getId()){
            return new ResponseDto(400, "You are not the owner of this card");
        }
        //create the text entity
        TextsEntity text = new TextsEntity();
        text.setText(textDto.getText());
        TextsEntity savedText = textsRepo.save(text);
        card.getTexts().add(savedText);
        cardRepo.save(card);
        return new ResponseDto(200, "Text added successfully");
    }

    @Override
    public ResponseDto deleteText(Long textId) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if(text == null){
            return new ResponseDto(400, "Text not found");
        }
        textsRepo.delete(text);
        return new ResponseDto(200, "Text deleted successfully");
    }

    @Override
    public ResponseDto updateText(TextDto textDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if(userEntity == null){
            return new ResponseDto(400, "User not found");
        }
        TextsEntity text = textsRepo.findById(textDto.getId()).orElse(null);
        if(text == null){
            return new ResponseDto(400, "Text not found");
        }
        text.setText(textDto.getText());
        textsRepo.save(text);
        return new ResponseDto(200, "Text updated successfully");
    }

    @Override
    public ResponseDto underline(Long textId, boolean status) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if(text == null){
            return new ResponseDto(400, "Text not found");
        }
        text.setUnderline(status);
        textsRepo.save(text);
        return new ResponseDto(200, status?"Underline added":"Underline removed");
    }

    @Override
    public ResponseDto highlight(Long textId, boolean status) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if(text == null){
            return new ResponseDto(400, "Text not found");
        }
        text.setHighlight(status);
        textsRepo.save(text);
        return new ResponseDto(200, status?"Highlight added":"Highlight removed");
    }

    @Override
    public ResponseDto lineThrough(Long textId, boolean status) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if(text == null){
            return new ResponseDto(400, "Text not found");
        }
        text.setLineThrough(status);
        textsRepo.save(text);
        return new ResponseDto(200, status?"Line through added":"Line through removed");
    }

    @Override
    public ResponseDto registerUser(UserDto userDto) {
        UserEntity user = userDto.userEntity(passwordEncoder, Roles.USER);
        //check if user exists
        UserEntity existingUser = cacheServices.getUser(user.getEmail());
        if(existingUser != null){
            return new ResponseDto(400, "User already exists");
        }
        cacheServices.updateUser(user);
        return new ResponseDto(200, "User created successfully");
    }

    @Override
    public ResponseDto adminCreateAdmin(UserDto userDto) {
        UserEntity user = userDto.userEntity(passwordEncoder, Roles.ADMIN);
        //check if user exists
        UserEntity existingUser = cacheServices.getUser(user.getEmail());
        if(existingUser != null){
            return new ResponseDto(400, "User already exists");
        }
        cacheServices.updateUser(user);
        return new ResponseDto(200, "Admin created successfully");
    }

    @Override
    public ResponseDto loginUser(LoginDto login) {
        UserEntity user = cacheServices.getUser(login.getEmail());
        if(user == null){
            return new ResponseDto(400, "User not found");
        }
        if(passwordEncoder.matches(login.getPassword(), user.getPassword())){
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("email", user.getEmail());
            userMap.put("role", user.getRole().name());
            userMap.put("funnName", user.getFullName());
            userMap.put("token", jwtService.generateToken(user));
            return new ResponseDto(200, "Login successful", userMap);
        }
        return new ResponseDto(400, "Invalid credentials");
    }

    @Override
    public ResponseDto userActivation(String email, boolean status) {
        //only changing user active to status
        UserEntity user = cacheServices.getUser(email);
        if(user == null){
            return new ResponseDto(400, "User not found");
        }
        user.setActive(status);
        cacheServices.updateUser(user);
        return new ResponseDto(200, status?"User activated":"User deactivated");
    }

    @Override
    public ResponseDto userDeletion(String email) {
        UserEntity user = cacheServices.getUser(email);
        if(user == null){
            return new ResponseDto(400, "User not found");
        }
        user.setDeleted(true);
        cacheServices.updateUser(user);
        return new ResponseDto(200, "User deleted successfully");
    }

    @Override
    public ResponseDto getCards(RequestCard requestCard) {
        return new ResponseDto(200, "Cards retrieved successfully", criteriaRepo.getCards(requestCard));
    }

    @Override
    public ResponseDto getUsers(RequestUsers requestUsers) {
        return new ResponseDto(200, "Users retrieved successfully", criteriaRepo.getUsers(requestUsers));
    }
    
}
