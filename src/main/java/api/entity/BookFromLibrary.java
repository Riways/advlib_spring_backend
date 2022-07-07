package api.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "book_library")
public class BookFromLibrary implements Serializable {

	private static final long serialVersionUID = -4897556002890943851L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "book_name")
	private String bookName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private Author author;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "amount_of_letters")
	private Integer amountOfLetters;

	@Column(name = "amount_of_sentences")
	private Integer amountOfSentences;

	@Column(name = "amount_of_words")
	private Integer amountOfWords;

	@Column(name = "readability", nullable = true)
	private Integer readability;

	@Column(length = 255, name = "path_to_file")
	private String pathToFile;

	public BookFromLibrary() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getAmountOfLetters() {
		return amountOfLetters;
	}

	public void setAmountOfLetters(Integer amountOfLetters) {
		this.amountOfLetters = amountOfLetters;
	}

	public Integer getAmountOfSentences() {
		return amountOfSentences;
	}

	public void setAmountOfSentences(Integer amountOfSentences) {
		this.amountOfSentences = amountOfSentences;
	}

	public Integer getAmountOfWords() {
		return amountOfWords;
	}

	public void setAmountOfWords(Integer amountOfWords) {
		this.amountOfWords = amountOfWords;
	}

	public Integer getReadability() {
		return readability;
	}

	public void setReadability(Integer readability) {
		this.readability = readability;
	}

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

}
