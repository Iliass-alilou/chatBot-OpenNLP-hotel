package Lemmatizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.util.InvalidFormatException;

public class LemmatizeTokens {

	public String[] lemmatizeTokens(String[] tokens, String[] posTags)
			throws InvalidFormatException, IOException {
		
		try (InputStream modelIn = new FileInputStream("en-lemmatizer.bin")) {

			// Tag sentence.
			LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
			String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);

			return lemmaTokens;

		}
	}
	
}
