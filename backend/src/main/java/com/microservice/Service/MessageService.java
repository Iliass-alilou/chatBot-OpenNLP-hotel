package com.microservice.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.microservice.BreakSentence.BreakSentences;
import com.microservice.DetectCategory.DetectCategory;
import com.microservice.DetectPOSTags.DetectPOSTags;
import com.microservice.Tokenization.Tokenizer;
import com.microservice.TrainCategorizer.TrainCategorizer;

import Lemmatizer.LemmatizeTokens;
import opennlp.tools.doccat.DoccatModel;

@Service
public class MessageService {
	
	private static Map<String, String> questionAnswer = new HashMap<>();
	
	static {
		questionAnswer.put("greeting", "Hello, how can I help you?");
		questionAnswer.put("person-by-room","2 persons by room");
		questionAnswer.put("price-room", "50$ for one night");
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

				answer = answer + " " + questionAnswer.get(category);

			}

			return answer;
	}

}
