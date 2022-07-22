package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import api.DAO.RoleRepository;
import api.entity.Author;
import api.entity.BookFromLibrary;
import api.entity.Role;
import api.entity.User;
import api.entity.WordFromDictionary;
import api.service.book_from_library_service.BookFromLibraryService;
import api.service.storage_service.FileSystemStorageService;
import api.service.text_analyze_service.SentencesAndLettersAnalyze;
import api.service.user_service.UserService;
import api.service.word_service.WordService;

@Component
public class DbInitialization {

	@Autowired
	private WordService wordService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SentencesAndLettersAnalyze sentencesAndLettersAnalyze;

	@Autowired
	private BookFromLibraryService bookFromLibraryService;

	@Autowired
	private FileSystemStorageService fileSystemStorageService;

	@Value("${dictionary.path}")
	String pathToDict;

	@Value("${alice-book.path}")
	String pathToSampleBook;

	@Value("${storage}")
	String pathToStorage;

	@EventListener(ApplicationReadyEvent.class)
	@Order(1)
	public void initRolesIfNotPresent() {
		if (roleRepository.findAll().size() != 0) {
			return;
		}
		Role admin = new Role("ADMIN", "Manages all");
		Role user = new Role("USER", "Can upload books");
		roleRepository.save(admin);
		roleRepository.save(user);
	}

	@EventListener(ApplicationReadyEvent.class)
	@Order(2)
	public void initDictIfNotPresent() {
		
		if (wordService.findAll().size() != 0) {
			return;
		}
		
		ClassLoader classLoader = null;
		classLoader = this.getClass().getClassLoader();
		
		InputStream is = classLoader.getResourceAsStream(pathToDict);
		List<WordFromDictionary> wordsToSave = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String word;
			while ((word = reader.readLine()) != null) {
				wordsToSave.add(new WordFromDictionary(word));
			}
			wordService.saveAll(wordsToSave);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	@Order(3)
	public void initAdminIfNotPresent() {
		String username = "root";
		if (!userService.isUsernameAvailable(username)) {
			return;
		}
		User admin = new User("root", "groot", "", true);
		userService.saveUserAsAdmin(admin);
	}

	@EventListener(ApplicationReadyEvent.class)
	@Order(4)
	public void initBookIfNotPresent() {
		long elements = bookFromLibraryService.getAllBooks().stream().filter((book) -> {
			if (book.getFileName().equals("Alice.txt"))
				return true;
			return false;
		}).count();
		if(elements!=0)
			return;
		File aliceBook;
		
		InputStream is = getClass().getClassLoader().getResourceAsStream(pathToSampleBook);
		
		
		aliceBook = fileSystemStorageService.store(is, "Alice.txt");

		int amountOfWords = bookFromLibraryService.getAmountOfWords(aliceBook);
		BookFromLibrary book = new BookFromLibrary();

		int amountOfSentences = sentencesAndLettersAnalyze.getAmountOfSentences(aliceBook);
		int amountOfLetters = sentencesAndLettersAnalyze.getAmountOfLetters(aliceBook);
		int readability = sentencesAndLettersAnalyze.calculateReadability(amountOfWords, amountOfSentences,
				amountOfLetters);

		Author author = new Author("Lewis Carrol");

		String bookName = "Alice";

		book.setAuthor(author);
		book.setBookName(bookName);
		book.setFileName("Alice.txt");
		book.setAmountOfLetters(amountOfLetters);
		book.setAmountOfSentences(amountOfSentences);
		book.setAmountOfWords(amountOfWords);
		book.setReadability(readability);
		book.setPathToFile(aliceBook.getAbsolutePath());
		bookFromLibraryService.saveBook(book);
	}

}
