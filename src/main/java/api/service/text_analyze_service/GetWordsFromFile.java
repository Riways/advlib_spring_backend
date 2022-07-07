package api.service.text_analyze_service;

import java.io.File;
import java.util.ArrayList;

import api.entity.WordAndCounter;

public interface GetWordsFromFile {

    ArrayList<WordAndCounter> getWordsInList(File fileToRead);
    ArrayList<WordAndCounter> getWordsInListWithPercentage(File fileToRead, int percentage);
    void checkWordsInDictionary(ArrayList<WordAndCounter> wordsToCheck);

}
