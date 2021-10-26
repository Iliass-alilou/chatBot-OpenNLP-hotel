package com.microservice.DetectCategory;

import java.io.IOException;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class DetectCategory {

	public  String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {

		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

		double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
		String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
		System.out.println("Category: " + category);

		return category;

	}
	
}
