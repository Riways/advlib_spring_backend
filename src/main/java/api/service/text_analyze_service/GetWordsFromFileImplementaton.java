package api.service.text_analyze_service;

import api.entity.WordAndCounter;
import api.service.word_service.WordServiceImlementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class GetWordsFromFileImplementaton implements GetWordsFromFile {

	Logger logger = LoggerFactory.getLogger(FilterWordsImplementation.class);

	private FilterWordsImplementation filterWordsImplementation;

	private WordServiceImlementation wordServiceImlementation;

	public GetWordsFromFileImplementaton() {
	}

	@Autowired
	public GetWordsFromFileImplementaton(WordServiceImlementation wordServiceImlementation,
			FilterWordsImplementation filterWordsImplementation) {
		this.wordServiceImlementation = wordServiceImlementation;
		this.filterWordsImplementation = filterWordsImplementation;
	}

	@Override
	public ArrayList<WordAndCounter> getWordsInList(File fileToRead) { // Get all words from file in ArrayList
		String str;
		ArrayList<WordAndCounter> words = new ArrayList<>();
		Pattern pattern = Pattern.compile("[a-z]+[']*[a-z]+");
		HashSet<String> wordsWithoutCounter = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileToRead))) {
			while ((str = reader.readLine()) != null) {
				str.toLowerCase();
				Matcher matcher = pattern.matcher(str);
				while (matcher.find()) {
					String word = matcher.group();
					if (wordsWithoutCounter.contains(word)) {
						for (int i = 0; i < words.size(); i++) {
							if (words.get(i).getWord().equals(word)) {
								words.get(i).setCounter(words.get(i).getCounter() + 1);
								break;
							}
						}
					} else {
						wordsWithoutCounter.add(word);
						words.add(new WordAndCounter(word, 1));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(words);
		logger.debug(words.size() + " words readed!");
		checkWordsInDictionary(words);
		logger.debug("Words checked in dictionary! " + words.size() + " words left in list");

		return words;
	}
	

	@Override
	public ArrayList<WordAndCounter> getWordsInListWithPercentage(File fileToRead, int percentage) {
		if (percentage < 0 || percentage > 100) {
			throw new ArithmeticException("Percentage must be between 0 and 100");
		}
		ArrayList<WordAndCounter> wordsWithPercentage = getWordsInList(fileToRead);
		wordsWithPercentage = filterWordsImplementation.getSpecificPercentOfWords(wordsWithPercentage, percentage);
		return wordsWithPercentage;
	}

	@Override
	public void checkWordsInDictionary(ArrayList<WordAndCounter> wordsToCheck) {
		HashSet<String> setOfWords = wordServiceImlementation.getOnlyWordsFromDictionary();
		for (int i = 0; i < wordsToCheck.size(); i++) {
			if (!setOfWords.contains(wordsToCheck.get(i).getWord())) {
				wordsToCheck.remove(i);
			}
		}
	}

	@Override
	public String toString() {
		return "GetWordsFromFileImplementaton{" + "fileToRead=" + '}';
	}

}