package com.example.se_final;

import com.example.se_final.entities.Reservation;
import com.example.se_final.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SeFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeFinalApplication.class, args);
	}



}
