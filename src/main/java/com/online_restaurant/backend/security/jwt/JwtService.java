package com.online_restaurant.backend.security.jwt;

import com.online_restaurant.backend.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Date;

@Service
public class JwtService  {

    private KeyPair key ;
    @Autowired
    JwtService(){
        key = Jwts.SIG.RS512.keyPair().build();
    }

    public String generateToke(User user){
        String token = Jwts.builder().header().keyId(user.getId().toString()).
                and().subject(user.getUsername()).claim("email",user.getEmail()).
                issuer(user.getEmail()).
                claim("pass",user.getPassword()).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+60L*10L*1000L)).
                signWith(key.getPrivate()).
                compact();

        return token;
    }


    public <T> T getClaims(String token, String claim, Class<T> type){
        return Jwts.parser().verifyWith(key.getPublic()).build().
                parseSignedClaims(token).getPayload().get(claim,type);
    }


    public boolean isExpired(String token){
        Long timeMilliSecond= getClaims(token,"exp", Long.class);
        return timeMilliSecond < System.currentTimeMillis();
    }



}
