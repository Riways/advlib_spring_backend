package api.service.user_service;

import api.DAO.RoleRepository;
import api.DAO.UserRepository;
import api.entity.Role;
import api.entity.User;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	PasswordEncoder passwordEncoder;

	Set<String> occupiedUsernames = null;
	
	Set<String> preservedEmails = null;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void saveUserAsUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.getRoleByName("USER");
		user.addRole(userRole);
		user.setEnabled(true);
		userRepository.save(user);
		updateOccupiedUsernames();
		updatePreservedEmails();
	}
	
	public void saveUserAsAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.getRoleByName("ADMIN");
		user.addRole(userRole);
		user.setEnabled(true);
		userRepository.save(user);
		updateOccupiedUsernames();
		updatePreservedEmails();
	}

	public Set<String> getAllUsernames() {
		return userRepository.getAllUsernames();
	}
	
	public Set<String> getAllEmails() {
		return userRepository.getAllEmails();
	}

	public boolean isUsernameAvailable(String username) {
    	if(occupiedUsernames == null) {
    		updateOccupiedUsernames();
    	}
    	return !occupiedUsernames.contains(username);
    }
	
	public boolean isEmailAvailable(String email) {
    	if(preservedEmails == null) {
    		updatePreservedEmails();
    	}
    	return !preservedEmails.contains(email);
    }
	
	private void updateOccupiedUsernames() {
		occupiedUsernames = userRepository.getAllUsernames();
	}
	
	private void updatePreservedEmails() {
		preservedEmails = userRepository.getAllEmails();
	}
}
