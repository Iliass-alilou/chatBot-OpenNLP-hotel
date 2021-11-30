package models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room {

	@Id
	private Long id;
	
	private String number;
	private double price;
	private int max_person;
	private boolean dipo;
	
	
	public Room(String number,double price,int max_person,boolean dipo){
		this.setDipo(dipo);
		this.setMax_person(max_person);
		this.setNumber(number);
		this.setPrice(price);
	}
}
