package com.stackroute.AuthenticationService.utils;

import com.stackroute.AuthenticationService.model.UserAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;

@PropertySource("classpath:application.properties")
public class TokenGenerator {

	@Value("${auth.key.value}")
//	private static String encodeKey;
	private static final long EXPIRATION_TIME = 864000000;  // 10 days


	public static String generateToken(UserAuth user) {
		return Jwts.builder()
				.setSubject(user.getType())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, "oscarmovie")
				.compact();
	}
}
