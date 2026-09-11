package com.example.carrental;

import com.example.carrental.service.CarRentalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarrentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrentalApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarRentalService carRentalService) {
		return args -> carRentalService.addRandomCars(5);
	}

}
