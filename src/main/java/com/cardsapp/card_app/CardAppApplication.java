package com.cardsapp.card_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cardsapp.card_app.Configs.RSACerts;
import com.cardsapp.card_app.Services.SeederService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
@EnableScheduling
@Slf4j
@EnableConfigurationProperties({RSACerts.class})
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
