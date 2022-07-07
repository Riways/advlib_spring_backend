package api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.entity.AuthenticationRequest;
import api.entity.UserToFrontend;
import api.security.JwtUtil;

@RestController
@CrossOrigin(origins="${frontend.localserver}")
@RequestMapping("/api/auth")
public class AuthenticationRestController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwTokenUtil;

	@PostMapping("/login")
	public UserToFrontend authenticate(@RequestBody AuthenticationRequest req) {

		Authentication auth = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());

		try {
			authenticationManager.authenticate(auth);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Bad username or password" , e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
		
		final String jwt = jwTokenUtil.generateToken(userDetails);

		return createUser(userDetails, jwt);
	}

	
	@PostMapping("/logout")
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(req, res, null);
	}
	
	private UserToFrontend createUser(UserDetails userDetails, String jwt) {
		UserToFrontend user = new UserToFrontend();
		user.setAuthoritiesFromUserDetails(  userDetails.getAuthorities());
		user.setEnabled(true);
		user.setJwt(jwt);
		user.setUsername(userDetails.getUsername());
		user.setExpirationTimeInMillis(jwTokenUtil.extractExpiration(jwt).getTime());
		return user;
	}
}
