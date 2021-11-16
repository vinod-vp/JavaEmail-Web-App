package com.JavaEmail;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class Jwt {

	public Jwt() {
	}

	public String GenerateToken(String username) {
		String secret = "kkJLKLKJLKdhOIU90YDIHGIP9090U09hioHUYHIOHUhhhjkhjkhjkgyuf7t78yuH987t";

		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                            SignatureAlgorithm.HS256.getJcaName());

		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
		        .claim("user", username)
		        .setSubject("user")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .signWith(hmacKey)
		        .compact();
		return jwtToken;

	}

	public Claims DecodeToken(String jwtToken) {

		String secret = "kkJLKLKJLKdhOIU90YDIHGIP9090U09hioHUYHIOHUhhhjkhjkhjkgyuf7t78yuH987t";

		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                            SignatureAlgorithm.HS256.getJcaName());
		Jws<Claims> jwt = Jwts.parserBuilder()
	            .setSigningKey(hmacKey)
	            .build()
	            .parseClaimsJws(jwtToken);
		return jwt.getBody();

	}


}
