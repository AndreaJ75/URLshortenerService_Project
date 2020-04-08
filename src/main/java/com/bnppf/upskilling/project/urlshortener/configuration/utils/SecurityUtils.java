package com.bnppf.upskilling.project.urlshortener.configuration.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserLogin(){

        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Login = " + login);

        return login;

    }

}
