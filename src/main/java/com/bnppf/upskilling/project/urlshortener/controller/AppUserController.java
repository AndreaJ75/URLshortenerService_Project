package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bnppf/urlservice/user")
public class AppUserController {


    /**
     * Declaration of AppUser Service
     */
    private AppUserService appUserService;


    /**
     * AppUser Service injection inside of the controller constructor (to be able to access Services created Method)
     *
     * @param appUserService
     */
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * Request/Response for AppUser creation
     *
     * @param appUser
     * @return appUserCreated
     */
    @PostMapping
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.createAppUser(appUser));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getListOfAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAppUserList());
    }


    @GetMapping("/{uid}")
    public ResponseEntity<AppUser> getAppUserByUID(@PathVariable("uid") String UID) {
        Optional<AppUser> userOptional = appUserService.getAppUserByUID(UID);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser appUser) {
        AppUser userToUpdate = appUserService.updateAppUser(appUser);

        if (userToUpdate != null) {
            return ResponseEntity.ok(userToUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/user/{appUserId}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long appUserId) {
        boolean status = appUserService.deleteAppUser(appUserId);

        if (status == true) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/List<{appUserId}>")
    public ResponseEntity<Void> deleteAppUserList(@PathVariable List<Long> appUserIdList) {
        List<Boolean> statusList = appUserService.deleteAppUserList(appUserIdList);

        ResponseEntity res = null;

        for (int i =0 ; i< statusList.size(); i++) {

            if (statusList.get(i) == true) {
                res = ResponseEntity.ok().body(null);
            } else {
                res = ResponseEntity.notFound().build();
            }
        } return res;
    }

    @GetMapping
    public String getCurrentUserLogin () {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}