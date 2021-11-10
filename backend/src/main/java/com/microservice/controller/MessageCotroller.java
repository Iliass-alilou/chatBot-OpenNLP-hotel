package com.microservice.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.Service.LanguageMapper;
import com.microservice.Service.MessageService;


import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;


@RestController
@RequestMapping(value = "/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageCotroller {
	
	@Autowired
	MessageService messageService;
	
	LanguageMapper languageMapper = new LanguageMapper();
	
	
	@GetMapping
	public String askQuestion (@RequestBody String userInput) throws FileNotFoundException, IOException, InterruptedException {
		File modelFile = new File(".\\langdetect-183.bin");
		LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
		LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
		Language[] languages = languageDetector.predictLanguages(userInput);
		
		String response = messageService.Response(userInput);
		
		return "Predicted language: "+ languageMapper.getLanguage(languages[0].getLang()) +"\n" +"Chat Response "+response ;
	}
	
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