package api.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;


public class UserToFrontend {

	private String jwt;

	private String username;

	private boolean enabled;
	
	private long expirationTimeInMillis;

	private List<String> authorities;


	public UserToFrontend() {
		super();
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
	public void setAuthoritiesFromUserDetails(Collection<? extends GrantedAuthority> authorities) {

		ArrayList<String> authoritiesInArray = new ArrayList<>();

		for (GrantedAuthority authority : authorities) {
			authoritiesInArray.add( authority.getAuthority());
		}
		
		this.authorities=authoritiesInArray;
		
	}

	public long getExpirationTimeInMillis() {
		return expirationTimeInMillis;
	}

	public void setExpirationTimeInMillis(long expirationTimeInMillis) {
		this.expirationTimeInMillis = expirationTimeInMillis;
	}


	
}
