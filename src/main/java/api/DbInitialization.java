package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import api.DAO.RoleRepository;
import api.entity.Role;
import api.entity.User;
import api.entity.WordFromDictionary;
import api.service.user_service.UserService;
import api.service.word_service.WordService;

@Component
public class DbInitialization {

	@Autowired
	WordService wordService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserService userService;

	@Value("${dictionary.path}")
	String pathToDict;

	@EventListener(ApplicationReadyEvent.class)
	public void initDictIfNotPresent() {
		if (wordService.findAll().size() != 0) {
			return;
		}
		Path dictLocation = Paths.get(System.getProperty("user.dir") + pathToDict);
		File dictFile = dictLocation.toFile();
		List<WordFromDictionary> wordsToSave = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(dictFile))) {
			String word;
			while ((word = reader.readLine()) != null) {
				wordsToSave.add(new WordFromDictionary(word));
			}
			wordService.saveAll(wordsToSave);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void initRoles() {
		if(roleRepository.findAll().size()!=0) {
			return;
		}
		Role admin = new Role("ADMIN","Manages all");
		Role user = new Role ("USER", "Can upload books");
		roleRepository.save(admin);
		roleRepository.save(user);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void initAdmin() {
		String username = "root";
		if(!userService.isUsernameAvailable(username)) {
			return;
		}
		User admin = new User("root", "groot", "", true);
		userService.saveUserAsAdmin(admin);
	}
	
}
