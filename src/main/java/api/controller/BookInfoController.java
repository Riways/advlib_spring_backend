package api.controller;

import api.entity.BookAndWords;
import api.entity.BookFromLibrary;
import api.entity.WordAndCounter;
import api.service.book_from_library_service.BookFromLibraryServiceImplementation;
import api.service.text_analyze_service.GetWordsFromFileImplementaton;
import api.service.word_service.WordServiceImlementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;

@RestController
@RequestMapping("api/words")
@CrossOrigin(origins="${frontend.localserver}")
public class BookInfoController {

    BookFromLibraryServiceImplementation bookFromLibraryServiceImplementation;

    WordServiceImlementation wordServiceImlementation;

    GetWordsFromFileImplementaton getWordsFromFileImplementaton;

    @Autowired
    public BookInfoController(BookFromLibraryServiceImplementation bookFromLibraryServiceImplementation, WordServiceImlementation wordServiceImlementation, GetWordsFromFileImplementaton getWordsFromFileImplementaton) {
        this.bookFromLibraryServiceImplementation = bookFromLibraryServiceImplementation;
        this.wordServiceImlementation = wordServiceImlementation;
        this.getWordsFromFileImplementaton = getWordsFromFileImplementaton;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public BookAndWords getBookInfoHandler(@PathVariable("id") int id)  {

        BookFromLibrary book = bookFromLibraryServiceImplementation.getBookById(id);
        File bookFile = new File(book.getPathToFile());

        ArrayList<WordAndCounter> listOfWords = getWordsFromFileImplementaton.getWordsInListWithPercentage(bookFile, 80);
        BookAndWords bookAndWords = new BookAndWords(book, listOfWords);
        
        return bookAndWords;
    }
}
