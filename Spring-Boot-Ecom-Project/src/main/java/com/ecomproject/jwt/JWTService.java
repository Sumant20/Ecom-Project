package com.springsecurity;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {
	
	 private static SecretKey secretKey;
	static {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            secretKey = keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

	public String getToken(String username) {
		Map<String,Object> claims=new HashMap<>();
		
		return Jwts.builder()
					.claims()
					.add(claims)
					.subject(username)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis()+60*60*50*10))
					.and()
					.signWith(secretKey)
					.compact();
		
		
	}

//	private SecretKey getKey() {
//		SecretKey value=null;
//		try {
//			KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk=keygen.generateKey();
//			value=sk;
//			//byte[] encodedKey = Base64.getEncoder().encode(value.getEncoded());
//           // secreteKey = Base64.getEncoder().encodeToString(encodedKey);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return value;
//	}

	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	private <T> T extractClaim(String token,Function<Claims,T> claimResolver) {
		Claims claim=extractAllClaims(token);
		return claimResolver.apply(claim);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();
	}
	
	public boolean validateToken(String token,UserDetails userDetails) {
		String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpirationToken(token).before(new Date());
	}

	private Date extractExpirationToken(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
}
