package com.example.librarymanagement;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

@SpringBootApplication
public class LibrarymanagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibrarymanagementApplication.class, args);
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Encoders.BASE64.encode(key.getEncoded());
		System.out.println(base64Key);
	}


}
