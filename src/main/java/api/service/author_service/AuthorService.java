package api.service.author_service;

import api.entity.Author;

import java.util.List;

public interface AuthorService {

    public void saveAuthor(Author author);

    public void saveAllAuthors(List<Author> authorList);

    public List<Author> getAllAuthors();

    public Author getAuthorById(int id);

    public void deleteAuthorById(int id);
}
