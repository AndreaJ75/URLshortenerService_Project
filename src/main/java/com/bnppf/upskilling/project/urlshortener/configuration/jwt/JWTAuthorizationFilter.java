package com.bnppf.upskilling.project.urlshortener.configuration.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    private String TOKEN_PREFIX = "Bearer ";
    private String HEADER_KEY = "Authorization";
    private String secretKey;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey){
        super(authenticationManager);
        this.secretKey = secretKey;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain chain) throws IOException, ServletException {


        String header = httpServletRequest.getHeader(HEADER_KEY);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_KEY);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(Base64.getDecoder().decode(secretKey)))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            //DEBUT ANDY TEST

            // FIN ANDY TEST
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}


