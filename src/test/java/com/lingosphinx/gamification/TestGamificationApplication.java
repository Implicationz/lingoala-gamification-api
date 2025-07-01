package com.lingosphinx.gamification;

import org.springframework.boot.SpringApplication;

public class TestGamificationApplication {

	public static void main(String[] args) {
		SpringApplication.from(GamificationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
