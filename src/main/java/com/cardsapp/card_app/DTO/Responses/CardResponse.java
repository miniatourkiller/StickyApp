package com.cardsapp.card_app.DTO.Responses;

import java.util.List;

import com.cardsapp.card_app.Entities.Card;
import com.cardsapp.card_app.Entities.TextsEntity;

import lombok.Data;

@Data
public class CardResponse {
    private Long id;
    private String color;
    private String title;
    private String dateCreated;
    private boolean reminder;

    private List<TextsResponse> texts;

    @Data
    public static class TextsResponse{
        private long id;
        private long cardId;
        private String text;
        private boolean lineThrough;
        private boolean highlight;
        private boolean underline;
        private String dateCreated;

        public TextsResponse(TextsEntity text, long cardId) {
            this.id = text.getId();
            this.cardId = cardId;
            this.text = text.getText();
            this.lineThrough = text.isLineThrough();
            this.highlight = text.isHighlight();
            this.underline = text.isUnderline();
        }
    }

    public CardResponse(Card card) {
        this.id = card.getId();
        this.color = card.getColor();
        this.title = card.getTitle();
        this.reminder = card.isReminder();
        this.dateCreated = card.getDateCreated();
        this.texts = card.getTexts().stream().map(text -> new TextsResponse(text, card.getId())).toList();
    }
}
