package api.service.word_service;

import api.entity.WordFromDictionary;

import java.util.List;


public interface WordService  {
    public void saveAll(List<WordFromDictionary> wordInDictionaries);
    public List<WordFromDictionary> findAll();
    public void save(WordFromDictionary wordFromDictionary);
}
