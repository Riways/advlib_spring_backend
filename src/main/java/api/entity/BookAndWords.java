package api.entity;

import java.util.ArrayList;

import javax.persistence.Entity;


public class BookAndWords  {
	
	
	private BookFromLibrary book;
	private ArrayList<WordAndCounter> words;
	
	public BookAndWords() {
		super();
	}
	
	public BookAndWords(BookFromLibrary book, ArrayList<WordAndCounter> words) {
		super();
		this.book = book;
		this.words = words;
	}
	
	public BookFromLibrary getBook() {
		return book;
	}
	public void setBook(BookFromLibrary book) {
		this.book = book;
	}
	public ArrayList<WordAndCounter> getWords() {
		return words;
	}
	public void setWords(ArrayList<WordAndCounter> words) {
		this.words = words;
	}
	
	
}
