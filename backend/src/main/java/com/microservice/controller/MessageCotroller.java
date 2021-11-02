package com.microservice.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
public class MessageCotroller {
	
	@Autowired
	MessageService messageService;
	
	LanguageMapper languageMapper = new LanguageMapper();
	
	

	@PostMapping
	public String AskQuestion (@RequestBody String userInput) throws FileNotFoundException, IOException, InterruptedException {
		File modelFile = new File(".\\langdetect-183.bin");
		LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
		LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
		Language[] languages = languageDetector.predictLanguages(userInput);
		
		String response = messageService.Response(userInput);
		
		
		return "Predicted language: "+ languageMapper.getLanguage(languages[0].getLang()) +"\n" +"Chat Response "+response ;
	}
}
