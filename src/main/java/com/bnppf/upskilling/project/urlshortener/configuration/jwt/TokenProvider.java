package com.bnppf.upskilling.project.urlshortener.configuration.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${app.jwt.secret:}")
    private String secretKey;

    public JWTToken createToken(Authentication authentication){

        String subject;
        if (authentication.getPrincipal() instanceof String) {
            subject = (String) authentication.getPrincipal();
        } else {
            subject = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        String tokenA = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(Algorithm.HMAC512(Base64.getDecoder().decode(secretKey)));


       JWTToken token = new JWTToken();
       token.setToken(tokenA);

       return token;
    }

}

