package com.cardsapp.card_app.Configs;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RSACerts(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
    
}
