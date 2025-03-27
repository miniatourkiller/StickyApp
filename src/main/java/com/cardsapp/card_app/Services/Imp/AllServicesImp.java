package com.cardsapp.card_app.Services.Imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardsapp.card_app.Configs.JWTService;
import com.cardsapp.card_app.DTO.CardDto;
import com.cardsapp.card_app.DTO.LoginDto;
import com.cardsapp.card_app.DTO.MailMessage;
import com.cardsapp.card_app.DTO.ResponseDto;
import com.cardsapp.card_app.DTO.TextDto;
import com.cardsapp.card_app.DTO.UserDto;
import com.cardsapp.card_app.DTO.Requests.RequestCard;
import com.cardsapp.card_app.DTO.Requests.RequestUsers;
import com.cardsapp.card_app.DTO.Responses.CardResponse;
import com.cardsapp.card_app.Entities.Card;
import com.cardsapp.card_app.Entities.TextsEntity;
import com.cardsapp.card_app.Entities.UserEntity;
import com.cardsapp.card_app.Enums.Roles;
import com.cardsapp.card_app.Repos.CardRepo;
import com.cardsapp.card_app.Repos.CriteriaRepo;
import com.cardsapp.card_app.Repos.TextsRepo;
import com.cardsapp.card_app.Services.AllServices;
import com.cardsapp.card_app.Services.CacheServices;
import com.cardsapp.card_app.utils.DateUtils;
import com.cardsapp.card_app.utils.Mapper;
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
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "rm_mails";

    @Override
    public ResponseDto addCard(CardDto cardDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }
        // create the card entity
        Card card = new Card();

        card.setColor("bg-" + cardDto.getColor() + "-100");
        card.setOwner(userEntity);
        card.setTitle(cardDto.getTitle());
        cardRepo.save(card);
        return new ResponseDto(200, "Card created successfully");
    }

    // for checking valid colors
    // private boolean isHexColor(String color){
    // return color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    // }
    @Override
    public ResponseDto deleteCard(Long cardId) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardId).orElse(null);
        if (card == null) {
            return new ResponseDto(400, "Card not found");
        }
        if (!card.getOwner().getEmail().equals(userEntity.getEmail())) {
            return new ResponseDto(400, "You are not the owner of this card");
        }
        card.setDeleted(true);
        cardRepo.save(card);
        return new ResponseDto(200, "Card deleted successfully");
    }

    @Override
    public ResponseDto updateCard(CardDto cardDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardDto.getId()).orElse(null);
        if (card == null) {
            return new ResponseDto(400, "Card not found");
        }
        if (!card.getOwner().getEmail().equals(userEntity.getEmail())) {
            return new ResponseDto(400, "You are not the owner of this card");
        }
        card.setColor("bg-" + cardDto.getColor() + "-100");
        card.setTitle(cardDto.getTitle());
        cardRepo.save(card);
        return new ResponseDto(200, "Card updated successfully");
    }

    @Override
    public ResponseDto addText(Long cardId, TextDto textDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());

        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }
        Card card = cardRepo.findById(cardId).orElse(null);
        if (card == null) {
            return new ResponseDto(400, "Card not found");
        }
        if (!card.getOwner().getEmail().equals(userEntity.getEmail())) {
            return new ResponseDto(400, "You are not the owner of this card");
        }
        // create the text entity
        TextsEntity text = new TextsEntity();
        text.setText(textDto.getText());
        TextsEntity savedText = textsRepo.save(text);
        card.getTexts().add(savedText);
        cardRepo.save(card);
        return new ResponseDto(200, "Text added successfully");
    }

    @Override
    public ResponseDto deleteText(Long textId, Long cardId) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }

        Card card = cardRepo.findById(cardId).orElse(null);
        if (card == null) {
            return new ResponseDto(400, "Card not found");
        }
        if (!card.getOwner().getEmail().equals(userEntity.getEmail())) {
            return new ResponseDto(400, "You are not the owner of this card");
        }

        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if (text == null) {
            return new ResponseDto(400, "Text not found");
        }
        card.getTexts().remove(text);
        cardRepo.save(card);
        textsRepo.delete(text);
        return new ResponseDto(200, "Text deleted successfully");
    }

    @Override
    public ResponseDto updateText(TextDto textDto) {
        UserEntity userEntity = cacheServices.getUser(UserName.userName());
        if (userEntity == null) {
            return new ResponseDto(400, "User not found");
        }
        TextsEntity text = textsRepo.findById(textDto.getId()).orElse(null);
        if (text == null) {
            return new ResponseDto(400, "Text not found");
        }
        text.setText(textDto.getText());
        textsRepo.save(text);
        return new ResponseDto(200, "Text updated successfully");
    }

    @Override
    public ResponseDto underline(Long textId) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if (text == null) {
            return new ResponseDto(400, "Text not found");
        }
        text.setUnderline(!text.isUnderline());
        if (text.isUnderline()) {
            text.setLineThrough(false);
            ;
        }
        textsRepo.save(text);
        return new ResponseDto(200, text.isUnderline() ? "Underline added" : "Underline removed");
    }

    @Override
    public ResponseDto highlight(Long textId) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if (text == null) {
            return new ResponseDto(400, "Text not found");
        }
        text.setHighlight(!text.isHighlight());
        textsRepo.save(text);
        return new ResponseDto(200, text.isHighlight() ? "Highlight added" : "Highlight removed");
    }

    @Override
    public ResponseDto lineThrough(Long textId) {
        TextsEntity text = textsRepo.findById(textId).orElse(null);
        if (text == null) {
            return new ResponseDto(400, "Text not found");
        }
        text.setLineThrough(!text.isLineThrough());

        if (text.isLineThrough()) {
            text.setUnderline(false);
        }

        textsRepo.save(text);
        return new ResponseDto(200, text.isLineThrough() ? "Line through added" : "Line through removed");
    }

    @Override
    public ResponseDto registerUser(UserDto userDto) {
        UserEntity user = userDto.userEntity(passwordEncoder, Roles.USER);
        // check if user exists
        UserEntity existingUser = cacheServices.getUser(user.getEmail());
        if (existingUser != null) {
            return new ResponseDto(400, "User already exists");
        }
        UserEntity savedUser = cacheServices.updateUser(user);
        String message = "Welcome to the sticky notes. With great power, comes great responsibility.";
        MailMessage mailMessage = new MailMessage("Reminder", savedUser.getEmail(), "You had set a reminder",
                savedUser.getFullName(), message);

        kafkaTemplate.send(topic, Mapper.classToString(mailMessage));
        return new ResponseDto(200, "User created successfully");
    }

    @Override
    public ResponseDto adminCreateAdmin(UserDto userDto) {
        UserEntity user = userDto.userEntity(passwordEncoder, Roles.ADMIN);
        // check if user exists
        UserEntity existingUser = cacheServices.getUser(user.getEmail());
        if (existingUser != null) {
            return new ResponseDto(400, "User already exists");
        }
        cacheServices.updateUser(user);
        return new ResponseDto(200, "Admin created successfully");
    }

    @Override
    public ResponseDto loginUser(LoginDto login) {
        UserEntity user = cacheServices.getUser(login.getEmail());
        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
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
        // only changing user active to status
        UserEntity user = cacheServices.getUser(email);
        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        user.setActive(status);
        cacheServices.updateUser(user);
        return new ResponseDto(200, status ? "User activated" : "User deactivated");
    }

    @Override
    public ResponseDto userDeletion(String email) {
        UserEntity user = cacheServices.getUser(email);
        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        user.setDeleted(true);
        cacheServices.updateUser(user);
        return new ResponseDto(200, "User deleted successfully");
    }

    @Override
    public ResponseDto getCards(RequestCard requestCard) {
        return new ResponseDto(200, "Cards retrieved successfully",
                criteriaRepo.getCards(requestCard, UserName.userName()).map(card -> new CardResponse(card)));
    }

    @Override
    public ResponseDto getUsers(RequestUsers requestUsers) {
        return new ResponseDto(200, "Users retrieved successfully", criteriaRepo.getUsers(requestUsers));
    }

    @Override
    public ResponseDto activateCardReminder(Long cardId, boolean status, String dateTime) {
        if (isTimeFormatGood(dateTime)) {
            Card card = cardRepo.findById(cardId).orElse(null);
            if (card == null) {
                return new ResponseDto(400, "Card not found");
            }
            card.setReminder(status);
            card.setReminderTime(DateUtils.dateToday(LocalDateTime.parse(dateTime)));
            cardRepo.save(card);
            return new ResponseDto(200, status ? "Card reminder activated" : "Card reminder deactivated");
        } else {
            return new ResponseDto(400, "Bad time format: yyyy-MM-dd'T'HH:mm:ss:zzzz");
        }
    }

    private boolean isTimeFormatGood(String dateTime) {
        try {
            LocalDateTime.parse(dateTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void sendReminders() {
        List<Card> cards = cardRepo.findByReminderAndReminderTimeReached(true, DateUtils.currentDate());
        log.info("Date: " + DateUtils.currentDate());
        log.info("Sending reminders: " + cards.size());
        List<Card> remindedCards = new ArrayList<>();
        for (Card card : cards) {
            String message = "You had set a reminder for card: <h1>" + card.getTitle() + "</h1> to be sent at <h2>"
                    + card.getReminderTime() + "</h2>";
            MailMessage mailMessage = new MailMessage("Reminder", card.getOwner().getEmail(), "You had set a reminder",
                    card.getOwner().getFullName(), message);
            kafkaTemplate.send(topic, Mapper.classToString(mailMessage));
            card.setReminder(false);
            card.setReminderTime(null);
            remindedCards.add(card);
        }
        cardRepo.saveAll(remindedCards);
    }

}
