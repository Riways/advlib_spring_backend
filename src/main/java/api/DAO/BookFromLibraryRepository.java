package api.DAO;

import api.entity.BookFromLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFromLibraryRepository extends JpaRepository<BookFromLibrary, Integer> {
}
