package api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import api.entity.APIError;
import api.service.book_from_library_service.BookFileNotValidException;
import api.service.storage_service.StorageException;
import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class ExceptionController {

	@Value("${spring.servlet.multipart.max-file-size}")
	String maxFileSize;
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(StorageException.class)
	public APIError handleStorageException(StorageException e) {
		APIError error = new APIError(e.getClass().getSimpleName(),  e.getMessage() );
		return error;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ExpiredJwtException.class)
	public APIError handleExpiredJwtException(ExpiredJwtException e) {
		APIError error = new APIError(e.getClass().getSimpleName(),  "Please log in" );
		return error;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BookFileNotValidException.class)
	public APIError handleBookFileNotValid(BookFileNotValidException e) {
		APIError error = new APIError(e.getClass().getSimpleName(),  e.getMessage() );
		return error;
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public APIError badCredentialsException(BadCredentialsException e) {
		APIError error = new APIError(e.getClass().getSimpleName(),  "Wrong username or password" );
		return error;
    }

}
