package com.example.librarymanagement.jwt;

import com.example.librarymanagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken){
        Claims claims = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(jwtToken).getPayload();
        System.out.println("Extracted Claims: " + claims);
        return claims;
    }



    public SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // User roles add pannudhu
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Ensure this is used!
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String jwtToken, User userDetails) {
        final String username = extractUsername(jwtToken);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken){
        Date expiration = extractExpiration(jwtToken);
        if (expiration == null) {
            System.out.println("JWT Expiration is null! Treating token as expired.");
            return true; // Treat as expired if expiration is null
        }
        return expiration.before(new Date());
    }


    private Date extractExpiration(String jwtToken){
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
