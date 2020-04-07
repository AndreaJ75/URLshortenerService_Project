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
@RequestMapping("/api/appuser")
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
     * AVOIR => QUAND CETTE ETAPE SE FAIT-ELLE => SECURISATION DE CREATION ROLE USER/ADMIN
     * depend de LDAP => VOIR COMMENT lES RATTACHER (créer user à partir de LDAP suite login/password?)
     * Request/Response for AppUser creation
     * @param appUser
     * @return appUserCreated
     */
    //=> OK testé
//    @PostMapping("/createuser")
//    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) {
//        return ResponseEntity.ok(appUserService.createAppUser(appUser));
//    }


    // ********************************************************************************
    // ***************             READ                        ************************
    // ********************************************************************************
    //=> OK testé
    @GetMapping("/admin/userall")
    public ResponseEntity<List<AppUser>> getListOfAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAppUserList());
    }

    //=> OK testé
    @GetMapping("/user/{uid}")
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


    // ********************************************************************************
    // ***************           READ   LOGIN ACCESS           ************************
    // ********************************************************************************
    // => OK testé
    @GetMapping("/user")
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
