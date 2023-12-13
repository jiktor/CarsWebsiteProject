package com.cars.backend.Services;

import com.cars.backend.Models.Dao.Enums.Brands;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
	//private static final String secretKey = "5A7134743777397A24423646294A404E335266556A586E3272357538782F4125";
	private static final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	public boolean isTokenValid(String token, UserDetails userDetails){
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims :: getExpiration);
	}
	//username === User.getEmail
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
	){
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
				.signWith(getSignIngKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String generateToken(UserDetails userDetails,
								String firstName,
								String lastName){
		return generateToken(new HashMap<>(){{
			put("fistName",firstName);
			put("lastName",lastName);
		}},userDetails);
	}

	public<T> T extractClaim(String token, Function<Claims,T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token){
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignIngKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignIngKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
