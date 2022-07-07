package api.service.book_from_library_service;

import api.entity.BookFromLibrary;

import java.io.File;
import java.util.List;

public interface BookFromLibraryService {
    public void saveBook(BookFromLibrary book);

    public void saveAllBooks(List<BookFromLibrary> bookList);

    public List<BookFromLibrary> getAllBooks();

    public BookFromLibrary getBookById(int id);

    public void deleteBookById(int id);
    
    public int getAmountOfWords(File book);
    

    
}
