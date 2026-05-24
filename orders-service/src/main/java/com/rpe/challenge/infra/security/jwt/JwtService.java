package com.rpe.challenge.infra.security.jwt;

import com.rpe.challenge.shared.utils.TimeDurationParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
	private final JwtProperties jwtProperties;

	public String generateToken(String subject, Map<String, Object> claims) {
		return Jwts.builder()
			.subject(subject)
			.claims(claims)
			.issuer("Challenge API")
			.issuedAt(new Date())
			.expiration(getExpiration())
			.signWith(getSecretKey())
			.compact();
	}

	public String extractSubject(String token) {
		return getClaims(token).getSubject();
	}

	public Instant extractExpiration(String token) {
		return getClaims(token).getExpiration().toInstant();
	}

	public boolean isTokenValid(String token, String subject) {
		try {
			Claims claims = getClaims(token);
			return claims.getSubject().equals(subject)
				&& claims.getExpiration().after(new Date());
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	private Date getExpiration() {
		return new Date(System.currentTimeMillis() + TimeDurationParser.toMillis(jwtProperties.getExpirationTime()));
	}

	private Claims getClaims(String token) {
		return parseClaims(token);
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
	}
}
