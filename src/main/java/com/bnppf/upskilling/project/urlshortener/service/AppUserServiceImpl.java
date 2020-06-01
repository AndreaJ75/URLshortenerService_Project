package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.controller.AppUserController;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.model.AuthorityLevel;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import com.bnppf.upskilling.project.urlshortener.vm.AppUserAng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppUserServiceImpl implements AppUserService {


    private AppUserRepository appUserRepository;
//    private Authority authority;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Create AppUser for user
     * @param appUser
     * @return created AppUser
     */

    @Override
    public AppUser createOrUpdateAppUser(AppUser appUser) {
        /**
         * Si user habilité (LDAP) alors création du user (ctrle fait dans Authentication)
         * La création du user se demandera lorsque celui-ci va s'authentifier pour la première fois
         * (donc dans l'AuthenticationController)
         */
        Optional<AppUser> appUserOptional =
                appUserRepository.findAppUserByUid(appUser.getUid());
        if (appUserOptional.isPresent()) {
            // Update user's firstName, name, email and UpdateDate
            appUserOptional.get().setFirstName(appUser.getFirstName());
            appUserOptional.get().setName(appUser.getName());
            appUserOptional.get().setEmail(appUser.getEmail());
            appUser.setUpdateDate(LocalDateTime.now());
            return appUserRepository.save(appUserOptional.get());
        } else {
            Authority authority = new Authority();
            authority.setId(2L);
            authority.setAuthorityLevel(AuthorityLevel.ROLE_USER);
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            appUser.setAuthorities(authorities);
            // Set creation and Update date to today
            appUser.setCreationDate(LocalDateTime.now());
            appUser.setUpdateDate(LocalDateTime.now());
            // Save User in DB
            return appUserRepository.save(appUser);
        }
    }

    @Override
    public AppUser createAppUserAuthority(Long appUserId) {
        /**
         * Update AppUser AuthorityLevel requested by Admin
         */
        Optional<AppUser> appUserOptional =
                appUserRepository.findById(appUserId);
        if (appUserOptional.isPresent()) {
            // Update user's authorityLevel
            Authority authority = new Authority();
            authority.setId(1L);
            authority.setAuthorityLevel(AuthorityLevel.ROLE_ADMIN);
            List<Authority> authorities = appUserOptional.get().getAuthorities();
            authorities.add(authority);
            appUserOptional.get().setAuthorities(authorities);
            return appUserRepository.save(appUserOptional.get());
        } else {
            // return Httpstatus error (user should already exists)
            return null;
        }
    }

    @Override
    public AppUser removeAppUserAuthority(Long appUserId) {
        /**
         * Update AppUser AuthorityLevel requested by Admin
         */
        Optional<AppUser> appUserOptional =
                appUserRepository.findById(appUserId);
        if (appUserOptional.isPresent()) {
            // get user's authorities
            List<Authority> authorities = appUserOptional.get().getAuthorities();
            // Remove authorityLevel ROLE_ADMIN (set on position 1 as users always
            // created with default ROLE_USER first
            authorities.remove(1);

            appUserOptional.get().setAuthorities(authorities);
            return appUserRepository.save(appUserOptional.get());
        } else {
            // return Httpstatus error (user should already exists)
            return null;
        }
    }

    /**
     * Find all existing AppUser
     *
     * @return List of all existing App users
     */
    @Override
    public Page<AppUser> getAppUserList(Pageable pageable) {

        return this.appUserRepository.findAll(pageable);
    }

    @Override
    public Page<AppUserAng> getAllAppUserPageWithHighestAuthority(Pageable pageable){

        Page<AppUser> appUserPage = this.appUserRepository.findAll(pageable);
        List<AppUserAng> appUserAngs = new ArrayList<>();

        // get user AuthorityLevel number :
        for (AppUser appUser: appUserPage) {
            AppUserAng appUserAng = new AppUserAng();
            appUserAng.setAppUser(appUser);
            boolean adminAutoLevel = false;
            List<Authority> authorities = appUser.getAuthorities();
            for (Authority authority : authorities) {
                if (authority.getAuthorityLevel().toString().equals("ROLE_ADMIN")) {
                    adminAutoLevel = true;
                }
            }

            if (adminAutoLevel == true) {
                appUserAng.setHighestAuthorityLevel("ROLE_ADMIN");
            } else {
                appUserAng.setHighestAuthorityLevel("ROLE_USER");
            }
            appUserAngs.add(appUserAng);
        }
        Page<AppUserAng> appUserAngPage = new PageImpl<>(appUserAngs,
                pageable, appUserAngs.size());
        return appUserAngPage;
//       return this.appUserRepository.findAllAppUserWithHighestAuthority(pageable);
    }

    @Override
    public List<String> getAppUserRoles(String appUserLogin){
        // Roles in Database
        Optional<AppUser> appUserOptional =
                this.appUserRepository.findAppUserByUid(appUserLogin);

        List<String> appUserRoles = new ArrayList<>();
        if (appUserOptional.isPresent()) {
            for (int i = 0; i < appUserOptional.get().getAuthorities().size(); i++) {
                appUserRoles.add(appUserOptional.get().getAuthorities().get(i).toString());
            }
        }
        return appUserRoles;
    }

    /**
     * Find an Appuser by its UID
     *
     * @param appUserUID
     * @return
     */
    @Override
    public Optional<AppUser> getAppUserByUID(String appUserUID) {
        return appUserRepository.findAppUserByUid(appUserUID);
    }

    /**
     * Delete AppUser from its Id
     *
     * @param appUserIdToDelete
     * @return status boolean(true/false)
     */
    @Override
    public void deleteAppUser(Long appUserIdToDelete) {
        if (appUserRepository.existsById(appUserIdToDelete)) {
            appUserRepository.deleteById(appUserIdToDelete);
        }
    }

    /**
     * Delete a List of AppUser by their Ids
     *
     * @param appUserIdListToDelete
     * @return status boolean(true/false) for each AppUser considered
     */
    @Override
    public void deleteAppUserList(List<Long> appUserIdListToDelete) {

        for (Long appUserId : appUserIdListToDelete) {
            if (appUserRepository.existsById(appUserId)) {
                appUserRepository.deleteById(appUserId);
            }
        }
    }
}
