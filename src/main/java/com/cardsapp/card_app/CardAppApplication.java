package com.cardsapp.card_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cardsapp.card_app.Services.SeederService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class CardAppApplication implements CommandLineRunner {
	private final SeederService seederService;
	public static void main(String[] args) {
		SpringApplication.run(CardAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Seeding user");
		seederService.seedUser();
	}

}
