package api.service.book_from_library_service;

import api.DAO.BookFromLibraryRepository;
import api.entity.BookFromLibrary;
import api.entity.WordAndCounter;
import api.service.text_analyze_service.GetWordsFromFile;
import api.service.text_analyze_service.SentencesAndLettersAnalyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookFromLibraryServiceImplementation implements BookFromLibraryService {

	@Autowired
	BookFromLibraryRepository bookFromLibraryRepository;

	@Autowired
	GetWordsFromFile getWordsFromFile;
	
	@Autowired
	SentencesAndLettersAnalyze sentencesAndLettersAnalyze; 

	@Override
	public void saveBook(BookFromLibrary book) {
		bookFromLibraryRepository.save(book);
	}

	@Override
	public void saveAllBooks(List<BookFromLibrary> bookList) {
		bookFromLibraryRepository.saveAll(bookList);
	}

	@Override
	public List<BookFromLibrary> getAllBooks() {
		return bookFromLibraryRepository.findAll();
	}

	@Override
	public BookFromLibrary getBookById(int id) {
		return bookFromLibraryRepository.getById(id);
	}

	@Override
	public void deleteBookById(int id) {
		bookFromLibraryRepository.deleteById(id);
	}

	@Override
	public int getAmountOfWords(File book) {
		ArrayList<WordAndCounter> words = getWordsFromFile.getWordsInList(book);
		return sentencesAndLettersAnalyze.getSummaryAmountOfWords(words);
	}


}
