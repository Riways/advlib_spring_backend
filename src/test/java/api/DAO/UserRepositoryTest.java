package api.DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;


	@Test
	void testGetAllUsernames() {
		Set<String> usernames = userRepo.getAllUsernames();
		assertNotNull(usernames);
	}

}
