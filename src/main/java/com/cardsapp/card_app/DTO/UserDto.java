package com.cardsapp.card_app.DTO;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cardsapp.card_app.Entities.UserEntity;
import com.cardsapp.card_app.Enums.Roles;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String fullName;
    private String dob;
    private String password;

    public UserEntity userEntity(PasswordEncoder passwordEncoder, Roles role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setFullName(fullName);
        userEntity.setDob(dob);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(role);
        return userEntity;
    }
}
