package com.app.fsbtechminiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class FsbtechMiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsbtechMiniProjectApplication.class, args);
	}

}
