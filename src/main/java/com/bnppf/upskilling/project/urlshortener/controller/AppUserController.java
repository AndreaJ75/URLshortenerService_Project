package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.vm.LoginAuthoLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appuser")
@CrossOrigin("*")
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
     *
     * Creation during Login done inside AuthenticationController calls
     * LDAP reference for Users's creation and authentication
     * For User Authorization level : defined for all with level ROLE_USER
     * Except for webmaster (ROLE_ADMIN)
     *
     */

    // ********************************************************************************
    // ***************             READ                        ************************
    // ********************************************************************************

    // get all Users (for Admin only)
    @GetMapping("/admin/userall")
    public ResponseEntity<List<AppUser>> getListOfAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAppUserList());
    }

    // get User by its uid
    @GetMapping("/user/{uid}")
    public ResponseEntity<AppUser> getAppUserByUID(@PathVariable("uid") String UID) {
        Optional<AppUser> userOptional = appUserService.getAppUserByUID(UID);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get current User's login
    @GetMapping("/user")
    public String getCurrentUserLogin () {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // get User's login and authorizationLevel
    @GetMapping("/getUserLoginAndAuthoLevel")
    public ResponseEntity<LoginAuthoLevel> getUserLoginAndAuthoLevel() {

        /**
         * Initialize a LoginAuthoLevel object
         */
        LoginAuthoLevel loginAuthoLevel = new LoginAuthoLevel();
        /**
         *  Check security Context (ensure user is still connected) and get userUID
         */
        String loginCon = SecurityUtils.getCurrentUserLogin();
        loginAuthoLevel.setLoginCon(loginCon);
        /**
         * Get User's security Level
         */
        Optional<AppUser> appUserOptional = appUserService.getAppUserByUID(loginCon);
        if (appUserOptional.isPresent()) {
            // get user AuthorityLevel number :
            List<Authority> authorities = appUserOptional.get().getAuthorities();
            boolean adminAutoLevel = false;
            for (Authority authority : authorities) {
                if (authority.getAuthorityLevel().toString().equals("ROLE_ADMIN")) {
                    adminAutoLevel = true;
                }
            }
            if (adminAutoLevel == true) {
                loginAuthoLevel.setAuthoLevel("ROLE_ADMIN");
            } else {
                loginAuthoLevel.setAuthoLevel("ROLE_USER");
            }
            return ResponseEntity.ok().body(loginAuthoLevel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ********************************************************************************
    // ***************              UPDATE                     ************************
    // ********************************************************************************

    //
    // Update User's data done from CustomerUserDetailsContextManager
    // call createOrUpdateAppUser Method to update dedicated User data:
    // its complete name, email, or updateDate
    //

    // ********************************************************************************
    // ***************              DELETE                     ************************
    // ********************************************************************************

    // Delete user from its Id
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
