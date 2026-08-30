package com.jwt.configurations;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenGenerator {
		
@Value("${jwt.secret}")
public String secretByteKey;

public SecretKey  generateSecretKey() {
	byte [] byteSecretKey=Base64.getDecoder().decode(secretByteKey);
			return Keys.hmacShaKeyFor(byteSecretKey);
} 

public String generateJwtToken( Long userId,Map<String, Object> claims ) {
	return Jwts.builder()
			.claims(claims)
			.subject(String.valueOf(userId))
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis()+1000L *60 *60))
			.signWith(generateSecretKey(),Jwts.SIG.HS256)
			.compact();
	}
public String extractUserId(String token) {
	return Jwts.parser()
			.verifyWith(generateSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}
}
