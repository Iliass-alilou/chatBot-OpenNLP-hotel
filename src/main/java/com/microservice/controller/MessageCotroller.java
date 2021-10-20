package com.microservice.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.Service.MessageService;

@RestController
@RequestMapping(value = "/message")
public class MessageCotroller {
	
	@Autowired
	MessageService messageService;

	@PostMapping
	public String AskQuestion (@RequestBody String userInput) throws FileNotFoundException, IOException, InterruptedException {
		
		String response = messageService.Response(userInput);
		
		return response ;
	}
}
