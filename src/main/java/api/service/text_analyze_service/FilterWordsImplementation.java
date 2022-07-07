package api.service.text_analyze_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.entity.WordAndCounter;

import java.util.*;


@Service
public class FilterWordsImplementation implements FilterWords {
	
	Logger logger = LoggerFactory.getLogger(FilterWordsImplementation.class);

	@Autowired
	SentencesAndLettersAnalyze sentencesAndLettersAnalyze;
	
    @Override
    public ArrayList<WordAndCounter> getSpecificPercentOfWords(ArrayList<WordAndCounter> listToFilter, int percentage) {

        int wordsSummary = sentencesAndLettersAnalyze.getSummaryAmountOfWords(listToFilter);
        
        if(wordsSummary == 1) {
        	return listToFilter;
        }
        int amountOfWordsThatMakePercentage = wordsSummary * percentage / 100;
        
        int counter = 0;
        int lastIndex = 0;
        for (int i = 0; counter <= amountOfWordsThatMakePercentage; i++) {
            counter += listToFilter.get(i).getCounter();
            lastIndex = i;
            if(counter >= amountOfWordsThatMakePercentage){
                logger.debug("Words summary: " + counter);
            }
        }
        logger.debug("Percentage: " + percentage + "%");
        logger.debug(lastIndex + " words out of " + listToFilter.size() + " constitute " + percentage + " % of the text");
        ArrayList<WordAndCounter> result = new ArrayList<>();
        for (int i = 0; i < lastIndex; i++) {
            result.add(listToFilter.get(i));
        }
        return result;
    }
    
    
}
