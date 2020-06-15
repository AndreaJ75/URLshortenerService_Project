package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.vm.LoginAuthoLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO cross origin à configurer direct dans websecurity (+ rajouter url exacte du serveur front 4200)
@RestController
@RequestMapping("/api/appUser")
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
    @GetMapping("/admin/appUserAll")
    public ResponseEntity<Page<AppUser>> getListOfAllAppUsers(
        @PageableDefault(size=10, page = 0, sort = {"uid"},
        direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(appUserService.getAppUserList(pageable));
    }

    // Get all AppUsers filtered on uid, name or firstName provided (for Admin only)
    @GetMapping("admin/getAppUserFiltered")
    public ResponseEntity<Page<AppUser>> getAppUsersFilteredOnCriteria(
            @RequestParam(value="name", required = false) String name,
            @PageableDefault(size=10, page = 0, sort = {"updateDate"},
                    direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(appUserService
                .getAppUsersFilteredsOnName(name,pageable));
    }


    // get User by its uid
    @GetMapping("/admin/userId/{uid}")
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
    @GetMapping("/getCurrentUserLoginAndAuthoLevel")
    public ResponseEntity<LoginAuthoLevel> getCurrentUserLoginAndAuthoLevel() {

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

    @PutMapping("/admin/createAutho")
    public ResponseEntity<AppUser> createAppUserAuthority(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.createAppUserAuthority(appUser.getId()));
    }

    @PutMapping("/admin/removeAutho")
    public ResponseEntity<AppUser> deleteAppUserAuthority(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.removeAppUserAuthority(appUser.getId()));
    }
    // ********************************************************************************
    // ***************              DELETE                     ************************
    // ********************************************************************************
    // Delete user from its Id
    @DeleteMapping("/admin/userId/{appUserId}")
    public void deleteAppUser(@PathVariable Long appUserId) {
        // Delete UrlLinks related to users and then delete Users
        appUserService.deleteAppUser(appUserId);
    }

}
