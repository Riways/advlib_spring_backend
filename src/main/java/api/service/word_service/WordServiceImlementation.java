package api.service.word_service;

import api.DAO.WordRepository;
import api.entity.WordFromDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class WordServiceImlementation implements WordService {


    @Autowired
    private WordRepository wordRepository;


    @Override
    public void saveAll(List<WordFromDictionary> wordInDictionaries) {
        wordRepository.saveAll(wordInDictionaries);
    }

    @Override
    public List<WordFromDictionary> findAll() {
        return wordRepository.findAll();
    }

    

    @Override
    public void save(WordFromDictionary wordFromDictionary) {
        wordRepository.save( wordFromDictionary);
    }


    public HashSet<String> getOnlyWordsFromDictionary(){
        return wordRepository.getWordsFromDictionary();
    }

    
    public ArrayList<String> getWordsFromDictionaryInList(){
    	return wordRepository.getWordsFromDictionaryInList();
    }
}
