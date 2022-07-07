package api.DAO;

import api.entity.User;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	@Query(value = "SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	@Query(value = "SELECT u.username FROM User u ")
	public Set<String> getAllUsernames();
	
	@Query(value = "SELECT u.email FROM User u ")
	public Set<String> getAllEmails();
	
}
