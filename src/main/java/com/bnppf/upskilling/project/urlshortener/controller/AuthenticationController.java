package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.configuration.jwt.JWTToken;
import com.bnppf.upskilling.project.urlshortener.configuration.jwt.TokenProvider;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.vm.LoginPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// TODO mettre la fonction d'authen dans un service

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private AppUserService appUserService;

    /**
     * Injection del'authenticationManager rendu possible grâce au @Bean créé dans la security configuration class
     * @param authenticationManager
     */
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenProvider tokenProvider,
                                    AppUserService appUserService){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.appUserService = appUserService;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticateUser(@RequestBody LoginPassword loginPassword) {

        /**
         * On instancie un objet UsernamePasswordAuthenticationToken qui implémente AuthenticateManager
         * (AuthenticateManager étant une Interface ne peut pas être instancier...alors que UsernamePasswordAuth..le peut)
         * L'objet UsernamePasswordAuth ... est créé grâce au login/mot de passe (= principal + credential)
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginPassword.getLogin(), loginPassword.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        /**
         * Security context : on définit le contexte correspondant à l'utilisateur (on le stocke dans security context
         * (stockage Mod thread local : 1 nouveau contexte de sécurité est créé pour chaque thread)
         * l'ensemble des contexte users connectés
         */

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body(tokenProvider.createToken(authentication));

    }

    @GetMapping("/getAuth")
    public ResponseEntity<Boolean> getAuthUser() {

        /**
         *  Check security Context to see if user is still connected and authenticated
         */

        return ResponseEntity.ok()
                .body(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }

}
