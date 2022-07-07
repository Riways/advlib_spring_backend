package api.service.author_service;

import api.DAO.AuthorRepository;
import api.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImplementation implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void saveAllAuthors(List<Author> authorList) {
        authorRepository.saveAll(authorList);
    }

    @Override
    public List<Author> getAllAuthors() {
        new ArrayList<>();
        authorRepository.findAll();
        return null;
    }

    @Override
    public Author getAuthorById(int id) {
        return authorRepository.getById(id);
    }

    @Override
    public void deleteAuthorById(int id) {
        authorRepository.deleteById(id);
    }
}
