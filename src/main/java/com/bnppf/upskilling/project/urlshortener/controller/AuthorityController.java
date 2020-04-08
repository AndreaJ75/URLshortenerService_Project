//package com.bnppf.upskilling.project.urlshortener.controller;
//
//import com.bnppf.upskilling.project.urlshortener.model.Authority;
//import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
//import com.bnppf.upskilling.project.urlshortener.service.AuthorityLevelService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/autolevel")
//@CrossOrigin("*")
//public class AuthorityController {
//
//    private AuthorityLevelService authorityLevelService;
//    private AppUserService appUserService;
//
//    public AuthorityController(AuthorityLevelService authorityLevelService, AppUserService appUserService){
//        this.authorityLevelService = authorityLevelService;
//        this.appUserService = appUserService;
//    }
//
//    @GetMapping
//    public ResponseEntity<Authority> getAutorityLevel(@RequestParam Long authorityId){
//        Optional<Authority> authorityOptional = authorityLevelService.getAuthorityLevel(authorityId);
//        if(authorityOptional.isPresent()){
//            return ResponseEntity.ok(authorityOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//    }
//
//    @PostMapping
//    public ResponseEntity<Authority> createAuthorityLevel(@RequestBody Authority authority){
//        return ResponseEntity.ok(authorityLevelService.createAuthoritylevel(authority));
//    }
//
//    @PutMapping
//    public ResponseEntity<Authority> updateAuthorityLevel(@RequestBody Authority authority){
//        return ResponseEntity.ok(authorityLevelService.updateAuthorityLevel(authority));
//    }
//
//    @DeleteMapping
//    public  void deleteAuthorityLevel(@RequestParam Long authorityId){
//        authorityLevelService.deleteAuthorityLevel(authorityId);
//    }
//}
