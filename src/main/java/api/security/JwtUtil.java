package api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {

	@Value("${jwt.secret-key}")
	private String SECRET_KEY;

	@Value("${jwt.expiration-time-millis}")
	private long expirationTimeMillis;

	public String extractUsername(String token) {
		return extractClaim(token, (Claims claims) -> claims.getSubject());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, (Claims claims) -> claims.getExpiration());
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllCLaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllCLaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
		return token;
	}
	
	public boolean validateToken( String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (userDetails.getUsername().equals(username)) && !isTokenExpired(token);
	}

}
