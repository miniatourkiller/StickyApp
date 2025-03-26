package com.cardsapp.card_app.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class MailMessage {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String destination;
    private String cc;
    private String bcc;
    private String subject;
    private String message;
    @Setter(AccessLevel.NONE)
    private String totalExpenses;
    private String name;
    private String heading;
    private MailType messageType;
    //for custom mail
    public MailMessage(String subject, String destination, String heading, String name, String message){
        this.subject = subject;
        this.message = message;
        this.heading = heading;
        this.name = name;
        this.destination = destination;
        this.messageType = MailType.OTHER;
    }

    public static enum MailType{
        OTHER
    }
}
