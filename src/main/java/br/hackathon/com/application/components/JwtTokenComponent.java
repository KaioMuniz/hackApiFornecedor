package br.hackathon.com.application.components;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenComponent {

	@Value("${jwt.secret}")
	private String secret;

	public UUID getIdFromToken(String token) {
		return UUID.fromString(getClaimFromToken(token, Claims::getSubject));
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
		return claimsResolver.apply(claims);
	}
}
