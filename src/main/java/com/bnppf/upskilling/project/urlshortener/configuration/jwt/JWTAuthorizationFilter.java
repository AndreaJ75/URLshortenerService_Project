package com.bnppf.upskilling.project.urlshortener.configuration.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.service.AuthorityLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    private String TOKEN_PREFIX = "Bearer ";
    private String HEADER_KEY = "Authorization";
    private String secretKey;

    private AppUserService appUserService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey,
                                  AppUserService appUserService) {
        super(authenticationManager);
        this.secretKey = secretKey;
        this.appUserService = appUserService;
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
            String userUid = JWT.require(Algorithm.HMAC512(Base64.getDecoder().decode(secretKey)))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userUid != null) {
                Optional<AppUser> appUserOptional = appUserService.getAppUserByUID(userUid);
                if (appUserOptional.isPresent()) {
                    List<Authority> authorities = appUserOptional.get().getAuthorities();
                    List<GrantedAuthority> grantedAuthorities =
                            authorities.stream()
                            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityLevel().toString()))
                            .collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(userUid, null, grantedAuthorities);
                }
                else {
                    System.out.println("Invalid UID authentication");
                }
            }
            return null;
        }
        return null;
    }
}


