package com.librarymanagement.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service

public class JwtService {
    @Value(("${jwt.secret}"))
    private String secretKey;

    @Value(("${jwt.expiration}"))
    private Long jwtExpiration;

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    private  String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return  Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }



    public Boolean isTokenValid(String jwtToken , UserDetails userdetails){
          final  String username = extractUsername(jwtToken);
          return ( userdetails.getUsername().equals(username)&& !isTokenExpired(jwtToken));
    }
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }
    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration) ;
    }
    public String extractUsername (String  jwtToken){
        return  extractClaims(jwtToken, Claims::getSubject) ;     // here Claims= to the payload of token
    }




    private <T> T extractClaims(String jwtToken, Function<Claims,T> claimResolver) {
        final Claims claims=extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
    public SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


}
