package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    private AppUserService appUserService;

    public CustomUserDetailsContextMapper(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx,
                                          String login,
                                          Collection<? extends GrantedAuthority> collection) {

         //If Authentication is OK && User not existing in DB
         // ==> Create User in DB

        String completeName = null;
        try {
            completeName = ctx.getAttributes().get("cn").get().toString();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        AppUser appUserToBeCreated = new AppUser();
        appUserToBeCreated.setCompleteName(completeName);
        appUserToBeCreated.setUid(login);
        // Set email to space (for the start)
        appUserToBeCreated.setEmail("");
        // Call service for user creation
        AppUser appUserCreated = appUserService.createOrUpdateAppUser(appUserToBeCreated);

        return new User(login,"",appUserCreated.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityLevel().toString()))
            .collect(Collectors.toList()));
    }

    @Override
    public void mapUserToContext(UserDetails userDetails, DirContextAdapter dirContextAdapter) {

    }

}


