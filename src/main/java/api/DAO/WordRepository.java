package api.DAO;


import api.entity.WordFromDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Cacheable;


@Cacheable
public interface WordRepository extends JpaRepository<WordFromDictionary,Integer> {

    @Query("select c.word from WordFromDictionary c")
    HashSet<String> getWordsFromDictionary();
    
    @Query("select c.word from WordFromDictionary c")
    ArrayList<String> getWordsFromDictionaryInList();
}
