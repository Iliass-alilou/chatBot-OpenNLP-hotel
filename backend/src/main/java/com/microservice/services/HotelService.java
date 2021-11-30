package com.microservice.services;

import java.util.List;

import models.Room;

public interface HotelService {
	public Room addRoom(Room room);
	public List<Room> getDisp(boolean dispo);
}