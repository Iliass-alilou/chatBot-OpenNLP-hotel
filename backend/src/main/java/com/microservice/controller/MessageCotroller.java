package com.microservice.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.Service.MessageService;

@RestController
@RequestMapping(value = "/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageCotroller {
	
	@Autowired
	MessageService messageService;

	@PostMapping
	public ResponseEntity<Resp> getAnswers(@RequestBody String userInput) throws FileNotFoundException, IOException, InterruptedException {
		
		String response = messageService.Response(userInput);
		
		return new ResponseEntity<Resp>(new Resp(response),HttpStatus.OK);
	}
}


class Resp{
	public String rsp;
	public Resp(String rsp) {
		this.rsp=rsp;
	}
}