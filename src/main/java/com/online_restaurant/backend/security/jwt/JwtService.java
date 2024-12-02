package com.online_restaurant.backend.security.jwt;

import com.online_restaurant.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService  {

    private KeyPair key ;
    @Autowired
    JwtService(){
        key = Jwts.SIG.RS512.keyPair().build();
    }

    public String generateToken(User user) {
        // Build the token
        String token = Jwts.builder()
                .setHeaderParam("kid", user.getId().toString()) // Sets the Key ID in the header
                .setSubject(user.getUsername())                // Sets the subject (typically the username)
                .claim("email", user.getEmail())               // Adds email as a custom claim
                .claim("pass", user.getPassword())             // Adds password as a custom claim (not recommended)
                .setIssuer(user.getEmail())                    // Sets the issuer (could be your system/app name instead)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets token issuance time
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // Sets token expiration (10 minutes)
                .signWith(key.getPrivate()) // Signs the token using the private key
                .compact();

        return token;
    }



    public <E> E getClaims(String token, String claim, Class<E> type){
        return Jwts.parser().verifyWith(key.getPublic()).build().
                parseSignedClaims(token).getPayload().get(claim,type);
    }


    public boolean isExpired(String token) {
        Long dataMillisecond = getClaims(token, "exp", Long.class);
        return new Date(dataMillisecond).before(new Date());
    }





}
