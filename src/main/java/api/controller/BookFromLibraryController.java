package api.controller;

import api.entity.Author;
import api.entity.BookFromLibrary;
import api.service.book_from_library_service.BookFileNotValidException;
import api.service.book_from_library_service.BookFromLibraryService;
import api.service.storage_service.FileSystemStorageService;
import api.service.text_analyze_service.SentencesAndLettersAnalyze;
import api.util.MultipartToFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "${frontend.localserver}")
@RequestMapping("api/books")
public class BookFromLibraryController {

	private BookFromLibraryService bookFromLibraryService;

	private FileSystemStorageService fileSystemStorageService;
	
	@Autowired
	private SentencesAndLettersAnalyze sentencesAndLettersAnalyze;
	
	@Autowired
	private MultipartToFile converter;

	@Autowired
	public BookFromLibraryController(BookFromLibraryService bookFromLibraryService,
			FileSystemStorageService fileSystemStorageService) {
		this.bookFromLibraryService = bookFromLibraryService;
		this.fileSystemStorageService = fileSystemStorageService;
	}

	@GetMapping("/list")
	List<BookFromLibrary> getBooks() {
		List<BookFromLibrary> bookz = bookFromLibraryService.getAllBooks();
		return bookz;
	}

	@DeleteMapping("/delete/{id}")
	int deleteBook(@PathVariable("id") int id) {
		BookFromLibrary book = bookFromLibraryService.getBookById(id);
		try {
			fileSystemStorageService.deleteBook(book.getPathToFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		bookFromLibraryService.deleteBookById(id);
		return id;
	}

	@PostMapping("/upload")
	public String addBook(HttpServletRequest req, @RequestPart("uploadedBook") MultipartFile file) throws Exception {
		File fileFromMultipart = converter.convert(file);
		
		int amountOfWords = bookFromLibraryService.getAmountOfWords(fileFromMultipart);
		if(amountOfWords == 0) {
			throw new BookFileNotValidException();
		}
		
		BookFromLibrary book = new BookFromLibrary();
		
		int amountOfSentences = sentencesAndLettersAnalyze.getAmountOfSentences(fileFromMultipart);
		int amountOfLetters = sentencesAndLettersAnalyze.getAmountOfLetters(fileFromMultipart);
		int readability = sentencesAndLettersAnalyze.calculateReadability(amountOfWords, amountOfSentences, amountOfLetters);
		
		Author author = new Author();
		String fullname = req.getParameter("authorsFirstName").trim() + " "
				+ req.getParameter("authorsLastName".trim());
		String bookName = req.getParameter("bookName");
		author.setFullName(fullname);
		book.setAuthor(author);
		book.setBookName(bookName.trim());
		book.setFileName(file.getOriginalFilename());
		book.setPathToFile(fileSystemStorageService.getLocation() + "\\" + file.getOriginalFilename());
		fileSystemStorageService.store(fileFromMultipart);
		book.setAmountOfSentences(amountOfSentences);
		book.setAmountOfWords(amountOfWords);
		book.setAmountOfLetters(amountOfLetters);
		book.setReadability(readability);
		bookFromLibraryService.saveBook(book);
		return book.getFileName();
	}

}
