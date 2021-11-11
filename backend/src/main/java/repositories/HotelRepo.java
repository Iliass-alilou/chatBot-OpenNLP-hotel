package repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import models.Room;


@Repository
public interface HotelRepo extends MongoRepository<Room, Long> {

	public default  Room addRoom(Room room) {
		return this.save(room);
	}

	public default List<Room> getDisp() {
		return this.findAll();
	}

}