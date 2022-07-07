package api.service.book_from_library_service;

public class BookFileNotValidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BookFileNotValidException() {
		super("Book file not valid. File should contain english words");
	}
}
