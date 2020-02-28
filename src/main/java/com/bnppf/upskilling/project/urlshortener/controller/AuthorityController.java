package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.service.AuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/autolevel")
public class AuthorityController {

    private AuthorityService authorityService;
    private AppUserService appUserService;

    public AuthorityController(AuthorityService authorityService, AppUserService appUserService){
        this.authorityService = authorityService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<Authority> getAutorityLevel(@RequestParam Long authorityId){
        Optional<Authority> authorityOptional = authorityService.getAuthorityLevel(authorityId);
        if(authorityOptional.isPresent()){
            return ResponseEntity.ok(authorityOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping
    public ResponseEntity<Authority> createAuthorityLevel(@RequestBody Authority authority){
        return ResponseEntity.ok(authorityService.createAuthoritylevel(authority));
    }

    @PutMapping
    public ResponseEntity<Authority> updateAuthorityLevel(@RequestBody Authority authority){
        return ResponseEntity.ok(authorityService.updateAuthorityLevel(authority));
    }

    @DeleteMapping
    public  void deleteAuthorityLevel(@RequestParam Long authorityId){
        authorityService.deleteAuthorityLevel(authorityId);
    }
}
