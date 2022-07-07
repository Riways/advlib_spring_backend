package api.DAO;


import api.entity.WordFromDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;

public interface WordRepository extends JpaRepository<WordFromDictionary,Integer> {

    @Query("select c.word from WordFromDictionary c")
    HashSet<String> getWordsFromDictionary();
}
