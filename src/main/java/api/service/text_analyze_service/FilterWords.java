package api.service.text_analyze_service;

import java.util.ArrayList;

import api.entity.WordAndCounter;

public interface FilterWords {
    public ArrayList<WordAndCounter> getSpecificPercentOfWords(ArrayList<WordAndCounter> listToFilter, int percentage);
}
