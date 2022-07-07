package api.service.text_analyze_service;

import java.io.File;
import java.util.ArrayList;

import api.entity.WordAndCounter;


public interface SentencesAndLettersAnalyze {
	
	public int getAmountOfSentences(File file);
	public int getAmountOfLetters(File file);
    public int calculateReadability(double amountOfWords, double amountOfSentences, double amountOfLetters) ;
    public int getSummaryAmountOfWords(ArrayList<WordAndCounter> words);

}
