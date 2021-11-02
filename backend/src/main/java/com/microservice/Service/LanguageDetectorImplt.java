//package com.microservice.Service;
//
//import java.io.File;
//import java.io.IOException;
//
//import opennlp.tools.langdetect.*;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class LanguageDetectorImplt {
//
//	
//	LanguageMapper languageMapper = new LanguageMapper();
//	
//	// load the trained Language Detector Model file
//	File modelFile = new File(".\\langdetect-183.bin");
//	
//	LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
//	
//  
//	LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
//	
//    
//    Language[] languages = languageDetector.predictLanguages(user message);
//	
//    
//    public String detectedLanguageString () {
//    	return "Predicted language: "+ languageMapper.getLanguage(languages[0].getLang());
//    }
//    //System.out.println("Predicted language: "+ languageMapper.getLanguage(languages[0].getLang()));
//    
//   
//}
