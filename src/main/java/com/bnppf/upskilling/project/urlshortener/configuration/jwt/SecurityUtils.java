package com.bnppf.upskilling.project.urlshortener.configuration.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getUserLogin(){

        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Login = " + login);

        return login;

    }

}
