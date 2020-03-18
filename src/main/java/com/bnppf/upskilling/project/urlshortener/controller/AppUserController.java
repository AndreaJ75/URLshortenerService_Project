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
@RequestMapping("/api/user")
public class AppUserController {

    /**
     * Declaration of AppUser Service
     */
    private AppUserService appUserService;

    /**
     * AppUser Service injection inside of the controller constructor (to be able to access Services created Method)
     * @param appUserService
     */
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // ********************************************************************************
    // ***************              CREATE                     ************************
    // ********************************************************************************
    /**
     * Request/Response for AppUser creation
     * @param appUser
     * @return appUserCreated
     */
    //=> OK testé
    @PostMapping
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.createAppUser(appUser));
    }

    // ********************************************************************************
    // ***************             READ                        ************************
    // ********************************************************************************
    //=> OK testé
    @GetMapping("/admin/all")
    public ResponseEntity<List<AppUser>> getListOfAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAppUserList());
    }

    //=> OK testé
    @GetMapping("admin/{uid}")
    public ResponseEntity<AppUser> getAppUserByUID(@PathVariable("uid") String UID) {
        Optional<AppUser> userOptional = appUserService.getAppUserByUID(UID);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ********************************************************************************
    // ***************              UPDATE                     ************************
    // ********************************************************************************

    //=> OK testé
    @PutMapping
    public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser appUser) {
        AppUser userToUpdate = appUserService.updateAppUser(appUser);

        if (userToUpdate != null) {
            return ResponseEntity.ok(userToUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ********************************************************************************
    // ***************           READ   LOGIN ACCESS           ************************
    // ********************************************************************************
    // => OK testé
    @GetMapping
    public String getCurrentUserLogin () {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // ********************************************************************************
    // ***************              DELETE                     ************************
    // ********************************************************************************

    //=> OK testé
    @DeleteMapping("admin/{appUserId}")
    public void deleteAppUser(@PathVariable Long appUserId) {
        appUserService.deleteAppUser(appUserId);
    }

    //=> A tester (ne marche pas pour l'heure)
    @DeleteMapping("/admin/{appUserIdList}>")
    public void deleteAppUserList(@PathVariable List<Long> appUserIdList) {
        appUserService.deleteAppUserList(appUserIdList);
    }
}
