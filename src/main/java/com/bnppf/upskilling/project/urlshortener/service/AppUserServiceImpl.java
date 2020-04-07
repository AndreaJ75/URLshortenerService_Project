package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.model.AuthorityLevel;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AppUserServiceImpl implements AppUserService {


    private AppUserRepository appUserRepository;
    private Authority authority;

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
            appUserOptional.get().setCompleteName(appUser.getCompleteName());
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

    /**
     * Find all existing AppUser
     *
     * @return List of all existing App users
     */
    @Override
    public List<AppUser> getAppUserList() {
        return this.appUserRepository.findAll();
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
