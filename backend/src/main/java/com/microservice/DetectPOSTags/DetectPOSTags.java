package com.microservice.DetectPOSTags;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class DetectPOSTags {

	public  String[] detectPOSTags(String[] tokens) throws IOException {
		try (InputStream modelIn = new FileInputStream("en-pos-maxent.bin")) {

			POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

			// Tag sentence.
			String[] posTokens = myCategorizer.tag(tokens);

			return posTokens;

		}
	}
}
