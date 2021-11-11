package com.microservice.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.BreakSentence.BreakSentences;
import com.microservice.DetectCategory.DetectCategory;
import com.microservice.DetectPOSTags.DetectPOSTags;
import com.microservice.Tokenization.Tokenizer;
import com.microservice.TrainCategorizer.TrainCategorizer;

import Lemmatizer.LemmatizeTokens;
import models.Room;
import opennlp.tools.doccat.DoccatModel;
import repositories.HotelRepo;

@Service
public class MessageService {
	
	private static Map<String, String> questionAnswer = new HashMap<>();
	
	
	@Autowired
	HotelRepo hotelRepo;
	
	static {

		questionAnswer.put("greeting-fr", "Bonjour! Monsieur comment je peux vous aider");
		questionAnswer.put("person-by-room-fr","2 personnes par chambre");
		questionAnswer.put("price-room-fr", " pour la nuit");
		questionAnswer.put("available-room-fr", "");
		questionAnswer.put("reserve-room-fr", "oui bien sur voila le lien : http://localhost:4200/#/forme-reservation");
		questionAnswer.put("conversation-continue-fr", "Avez vous une autre question?");
		questionAnswer.put("conversation-complete-fr", "Trés bonne journée");
		
		
		questionAnswer.put("greeting", "Hello, how can I help you?");
		questionAnswer.put("person-by-room","2 persons by room");
		questionAnswer.put("price-room", " for one night");
		questionAnswer.put("available-room", "");
		questionAnswer.put("reserve-room", "yeah Of course, wait a minute");
		questionAnswer.put("conversation-continue", "What else can I help you with?");
		questionAnswer.put("conversation-complete", "Nice chatting with you. Bybye.");
	}

	public String Response(String userInput) throws FileNotFoundException, IOException, InterruptedException {
		
		TrainCategorizer trainCategorizer = new TrainCategorizer();
		DoccatModel model = trainCategorizer.trainCategorizerModel();
		
			BreakSentences breakSentences = new BreakSentences();
			String[] sentences = breakSentences.breakSentences(userInput);

			String answer = "";

			for (String sentence : sentences) {

				Tokenizer tokenizer = new Tokenizer();
				String[] tokens = tokenizer.tokenizeSentence(sentence);

				DetectPOSTags  detectPOSTags = new DetectPOSTags();
				String[] posTags = detectPOSTags.detectPOSTags(tokens);

				LemmatizeTokens limLemmatizeTokens = new LemmatizeTokens();
				String[] lemmas = limLemmatizeTokens.lemmatizeTokens(tokens, posTags);

				DetectCategory detectCategory = new DetectCategory();
				String category = detectCategory.detectCategory(model, lemmas);
				
				if(category.equals("available-room-fr")) {
					List<Room> rooms = 	hotelRepo.findAll();
					System.out.println(rooms.get(0));
					answer="oui, il reste que 2 chambres ";
				}else if(category.equals("available-room")) {
					answer="Yeah, there are 2rooms";
				}else if(category.equals("price-room") || category.equals("price-room-fr")) {
					answer += "50$ " + questionAnswer.get(category);
				}
				
				answer += questionAnswer.get(category);

			}

			return answer;
	}

}
