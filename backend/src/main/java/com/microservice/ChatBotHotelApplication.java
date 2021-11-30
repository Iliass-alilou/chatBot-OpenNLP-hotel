package com.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.microservice.services.HotelService;

import models.Room;
import repositories.HotelRepo;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = HotelRepo.class)
public class ChatBotHotelApplication  implements ApplicationRunner{
	
	@Autowired
	HotelService hotelService;

	public static void main(String[] args) {
		SpringApplication.run(ChatBotHotelApplication.class, args);
	}
	
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		//hotelService.addRoom(new Room(1L,"200",50,2, true));
		//hotelService.addRoom(new Room(2L,"201",50,2, true));
		//hotelService.addRoom(new Room(3L,"202",80,4, true));
		//hotelService.addRoom(new Room(5L,"300",90,4, true));
	}
	
}
