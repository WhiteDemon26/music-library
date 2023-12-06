package com.example.musiclibrary;


import com.example.musiclibrary.model.ProfileConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(ProfileConfiguration.class)
public class MusiclibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusiclibraryApplication.class, args);
	}
}