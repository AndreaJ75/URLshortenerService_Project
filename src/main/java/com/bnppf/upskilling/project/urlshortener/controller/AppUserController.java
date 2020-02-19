package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bnppf/urlshortener")
public class AppUserController {


    /**
     * Declaration of AppUser Service
     */
    private AppUserService appUserService;


    /**
     * AppUser Service injection inside of the controller constructor (to be able to access Services created Method)
     * @param appUserService
     */
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    /**
     * Request/Response for AppUser creation
     * @param appUser
     * @return appUserCreated
     */
    @PostMapping
    public ResponseEntity<AppUser> createAppUser(AppUser appUser) {
        return ResponseEntity.ok(appUserService.createAppUser(appUser));
    }


    @GetMapping
    public ResponseEntity<List<AppUser>> getListOfAllAppUsers(){
        return ResponseEntity.ok(appUserService.getAppUserList());
    }


}
