package com.cardsapp.card_app.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserName {
    public static String userName(){
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "Anonymous";
        }
    }
}