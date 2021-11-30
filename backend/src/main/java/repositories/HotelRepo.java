package repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import models.Room;


public interface HotelRepo extends MongoRepository<Room, Long> {
	List<Room> findByDipo(boolean dispo);
		
}