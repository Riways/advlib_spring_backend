package api.service.text_analyze_service;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.entity.WordAndCounter;


@Component
public class SentencesAndLettersAnalyzeImpl implements SentencesAndLettersAnalyze {

	@Autowired
	CommonMethodsTextService textService;

	@Override
	public int getAmountOfSentences(File file) {
		String text;
		int amountOfSentences=0;
		text = textService.getTextInStringFromFile(file);
		Pattern pattern = Pattern.compile("(?<!\\w\\.\\w.)(?<=\\.|\\?|\\!)\\s");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			amountOfSentences++;
		}
		return amountOfSentences;
	}

	@Override
	public int getAmountOfLetters(File file) {
		String text;
		int amountOfLetters=0;
		text = textService.getTextInStringFromFile(file);
		text = text.toLowerCase();
		char[] chars = text.toCharArray();
		for(int i=0; i<chars.length; i++) {
			if(chars[i]>='a' && chars[i]<='z') {
				amountOfLetters++;
			}
		}
		return amountOfLetters;
	}
	
	@Override
	public int calculateReadability(double amountOfWords, double amountOfSentences, double amountOfLetters) {
		
		//average number of letters per 100 words in the text
		double l = amountOfLetters /amountOfWords*100;
		
		//average number of sentences per 100 words in the text
		double s = amountOfSentences/amountOfWords*100;
		
		double readability = (0.0588*l - 0.296*s -15.8);
		
		int result = (int) Math.round(readability);
		return result < 16 ? result : 16;
	}
	
	public int getSummaryAmountOfWords(ArrayList<WordAndCounter> words) {
    	int counter = 0;
        for (int i = 0; i < words.size(); i++) { //Count summary amount of words
            counter += words.get(i).getCounter();
        }
        return counter;
    }

}
