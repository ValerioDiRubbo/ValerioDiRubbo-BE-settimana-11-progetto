package it.epicode.be.catalogolibri.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.epicode.be.catalogolibri.service.security.UserDetailsImpl;


/**
 * Classe utility per JWT
 *
 */
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expirationms}")
	private Long jwtExpirationMs;

	
	/**
	 * Genera un token JWT impostando
	 * - data di creazione
	 * - data di scadenza
	 * - username utente loggato
	 * e lo firma con la chiave segreta e l'algoritmo HS512
	 * 
	 * @param authentication informazioni relative all'utente
	 * @return stringa corrispondente al token
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Date now = new Date();
		Date exp = new Date((now).getTime() + jwtExpirationMs);
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(now).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * Estrae lo username dal token 
	 * 
	 * @param token JWT
	 * @return username 
	 */
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Effettua la validazione del token 
	 * 
	 * @param authToken toke JWT
	 * @return true se il token è valido, false altrimenti
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}