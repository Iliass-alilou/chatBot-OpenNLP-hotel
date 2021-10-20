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

	/*
	 * Define answers for each given category.
	 */
	static {
		questionAnswer.put("greeting", "Hello, how can I help you?");
		questionAnswer.put("product-inquiry",
				"Product is a TipTop mobile phone. It is a smart phone with latest features like touch screen, blutooth etc.");
		questionAnswer.put("price-inquiry", "Price is $300");
		questionAnswer.put("conversation-continue", "What else can I help you with?");
		questionAnswer.put("conversation-complete", "Nice chatting with you. Bbye.");

	}

	public String Response(String userInput) throws FileNotFoundException, IOException, InterruptedException {
		
		// Train categorizer model to the training data we created.
		TrainCategorizer trainCategorizer = new TrainCategorizer();
		DoccatModel model = trainCategorizer.trainCategorizerModel();
		
		
		//while (true) {

			// Get chat input from user.
		    System.out.println("##### You:");
			
			// Break users chat input into sentences using sentence detection.
			BreakSentences breakSentences = new BreakSentences();
			String[] sentences = breakSentences.breakSentences(userInput);

			String answer = "";
			//boolean conversationComplete = false;

			// Loop through sentences.
			for (String sentence : sentences) {

				// Separate words from each sentence using tokenizer.
				Tokenizer tokenizer = new Tokenizer();
				String[] tokens = tokenizer.tokenizeSentence(sentence);

				// Tag separated words with POS tags to understand their gramatical structure.
				DetectPOSTags  detectPOSTags = new DetectPOSTags();
				String[] posTags = detectPOSTags.detectPOSTags(tokens);

				// Lemmatize each word so that its easy to categorize.
				LemmatizeTokens limLemmatizeTokens = new LemmatizeTokens();
				String[] lemmas = limLemmatizeTokens.lemmatizeTokens(tokens, posTags);

				// Determine BEST category using lemmatized tokens used a mode that we trained
				// at start.
				DetectCategory detectCategory = new DetectCategory();
				String category = detectCategory.detectCategory(model, lemmas);

				// Get predefined answer from given category & add to answer.
				answer = answer + " " + questionAnswer.get(category);

				// If category conversation-complete, we will end chat conversation.
//				if ("conversation-complete".equals(category)) {
//					conversationComplete = true;
//				}
			}

			// Print answer back to user. If conversation is marked as complete, then end
			// loop & program.
			System.out.println("##### Chat Bot: " + answer);
			return answer;
//			if (conversationComplete) {
//				break;
//			}

		//}
		
		//return null;
	}

}
