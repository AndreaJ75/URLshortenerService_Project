package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.configuration.jwt.JwtToken;
import com.bnppf.upskilling.project.urlshortener.configuration.jwt.TokenProvider;
import com.bnppf.upskilling.project.urlshortener.vm.LoginPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    /**
     * Injection del'authenticationManager rendu possible grâce au @Bean créé dans la security configuration class
     * @param authenticationManager
     */
    public AuthentificationController(AuthenticationManager authenticationManager, TokenProvider tokenProvider){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authenticateUser(@RequestBody LoginPassword loginPassword){

        /**
         * On instancie un objet UsernamePasswordAuthenticationToken qui implémente AuthenticateManager
         * (AuthenticateManager étant une Interface ne peut pas être instancier...alors que Username....le peut)
         * L'objet Username ... est créé grâce à login/mot de passe (= principal + credential)
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

}
