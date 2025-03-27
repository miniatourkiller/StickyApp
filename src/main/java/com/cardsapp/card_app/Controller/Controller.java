package com.cardsapp.card_app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cardsapp.card_app.DTO.*;
import com.cardsapp.card_app.DTO.Requests.RequestCard;
import com.cardsapp.card_app.DTO.Requests.RequestUsers;
import com.cardsapp.card_app.Services.AllServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sticky/api/v1")
@RequiredArgsConstructor
public class Controller {
    private final AllServices allServices;

    private ResponseEntity<ResponseDto> getResponseEntity(ResponseDto responseDto) {
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
    }

    @PostMapping("/card")
    public ResponseEntity<ResponseDto> addCard(@RequestBody CardDto cardDto) {
        return getResponseEntity(allServices.addCard(cardDto));
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<ResponseDto> deleteCard(@PathVariable Long cardId) {
        return getResponseEntity(allServices.deleteCard(cardId));
    }

    @PutMapping("/card")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {
        return getResponseEntity(allServices.updateCard(cardDto));
    }

    @PostMapping("/card/{cardId}/text")
    public ResponseEntity<ResponseDto> addText(@PathVariable Long cardId, @RequestBody TextDto textDto) {
        return getResponseEntity(allServices.addText(cardId, textDto));
    }

    @DeleteMapping("/text/{textId}")
    public ResponseEntity<ResponseDto> deleteText(@PathVariable Long textId) {
        return getResponseEntity(allServices.deleteText(textId));
    }

    @PutMapping("/text")
    public ResponseEntity<ResponseDto> updateText(@RequestBody TextDto textDto) {
        return getResponseEntity(allServices.updateText(textDto));
    }

    @PutMapping("/text/{textId}/underline")
    public ResponseEntity<ResponseDto> underline(@PathVariable Long textId, @RequestParam boolean status) {
        return getResponseEntity(allServices.underline(textId, status));
    }

    @PutMapping("/text/{textId}/highlight")
    public ResponseEntity<ResponseDto> highlight(@PathVariable Long textId, @RequestParam boolean status) {
        return getResponseEntity(allServices.highlight(textId, status));
    }

    @PutMapping("/text/{textId}/line-through")
    public ResponseEntity<ResponseDto> lineThrough(@PathVariable Long textId, @RequestParam boolean status) {
        return getResponseEntity(allServices.lineThrough(textId, status));
    }

    @PostMapping("/all/user/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserDto userDto) {
        return getResponseEntity(allServices.registerUser(userDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/create-admin")
    public ResponseEntity<ResponseDto> adminCreateAdmin(@RequestBody UserDto userDto) {
        return getResponseEntity(allServices.adminCreateAdmin(userDto));
    }

    @PostMapping("/all/user/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        return getResponseEntity(allServices.loginUser(loginDto));
    }

    @PutMapping("/user/activation")
    public ResponseEntity<ResponseDto> userActivation(@RequestParam String email, @RequestParam boolean status) {
        return getResponseEntity(allServices.userActivation(email, status));
    }

    @DeleteMapping("/user/{userEmail}")
    public ResponseEntity<ResponseDto> userDeletion(@PathVariable String userEmail) {
        return getResponseEntity(allServices.userDeletion(userEmail));
    }

    @PostMapping("/cards")
    public ResponseEntity<ResponseDto> getCards(@RequestBody RequestCard requestCard) {
        return getResponseEntity(allServices.getCards(requestCard));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<ResponseDto> getUsers(@RequestBody RequestUsers requestUsers) {
        return getResponseEntity(allServices.getUsers(requestUsers));
    }

    @PutMapping("/card/{cardId}/reminder")
    public ResponseEntity<ResponseDto> activateCardReminder(@PathVariable Long cardId, @RequestParam boolean status, @RequestParam String dateTime) {
        return getResponseEntity(allServices.activateCardReminder(cardId, status, dateTime));
    }
}
