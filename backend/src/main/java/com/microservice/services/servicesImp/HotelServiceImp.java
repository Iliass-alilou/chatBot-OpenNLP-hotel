package com.microservice.services.servicesImp;

import java.lang.reflect.Array;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.microservice.services.HotelService;
import models.Room;
import repositories.HotelRepo;

@Service
public class HotelServiceImp implements HotelService{

	@Autowired
	HotelRepo hotelRepo;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public  Room addRoom(Room room) {
		return hotelRepo.save(room);
	}

	@Override
	public  List<Room> getDisp(boolean dispo) {
		return hotelRepo.findByDipo(dispo);
	}
	
}
