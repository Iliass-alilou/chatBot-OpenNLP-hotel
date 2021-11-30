package com.microservice.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.BreakSentence.BreakSentences;
import com.microservice.DetectCategory.DetectCategory;
import com.microservice.DetectPOSTags.DetectPOSTags;
import com.microservice.Tokenization.Tokenizer;
import com.microservice.TrainCategorizer.TrainCategorizer;
import com.microservice.services.HotelService;

import Lemmatizer.LemmatizeTokens;
import models.Room;
import opennlp.tools.doccat.DoccatModel;

@Service
public class MessageService {

	private static Map<String, String> questionAnswer = new HashMap<>();
	
	@Autowired
	HotelService hotelService;
	
		
	static {

		questionAnswer.put("greeting-fr", "Bonjour! comment je peux vous aider");
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
		questionAnswer.put("reserve-room", "yeah Of course, this is the link of form http://localhost:4200/forme-reservation");
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
					List<Room> rooms = 	hotelService.getDisp(true);
					answer="oui, il reste que "+rooms.size()+" chambres ";
				}else if(category.equals("available-room")) {
					List<Room> rooms = 	hotelService.getDisp(true);
					answer="Yeah, there are "+rooms.size()+" rooms";
				}else if(category.equals("price-room") || category.equals("price-room-fr")) {
					
					Set<Double> set = new HashSet<Double>();
					
					List<Room> rooms = 	hotelService.getDisp(true);
					
					rooms.forEach(r->{
						set.add(r.getPrice());
					});
					
					int i=0;
					for (Iterator<Double> it = set.iterator(); it.hasNext(); ) {
						double f = it.next();
						
						if(category.equals("price-room-fr")) {
							if(i==0) answer+= "Il y a des chambre de "+f+"$ ";
					        else answer += " et d'autre "+f+"$ ";
						}else {
							if(i==0) answer+= "Some for "+f+"$ ";
					        else answer += " and other "+f+"$ ";
						}
				        i++;
				    }
					
					answer+=" "+questionAnswer.get(category);
					
				}else {
					answer+=" "+questionAnswer.get(category);
				}

			}

			return answer;
	}

}
