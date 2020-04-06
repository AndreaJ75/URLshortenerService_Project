package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils.getCurrentUserLogin;

@Service
public class AppUserServiceImpl implements AppUserService {


    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Create AppUser for user
     * @param appUserToBeCreated
     * @return created AppUser
     */

    @Override
    public AppUser createAppUser(AppUser appUserToBeCreated) {
        /**
         * Si user habilité (LDAP) alors création du user (ctrle fait dans URL Link)
         * La création du user se demandera lorsque celui-ci veut se créer un URLLink
         * (donc dans l'UrlLinkController)
         */
            return appUserRepository.save(appUserToBeCreated);

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
     * Update AppUser for user
     *
     * @param appUserToUpdated
     * @return User updated
     */
    @Override
    public AppUser updateAppUserForUser(AppUser appUserToUpdated) {

        // User can only update name/firstName/email
        Optional<AppUser> appUserOptional = appUserRepository.findById(appUserToUpdated.getId());

        if (appUserOptional.isPresent()) {
            // Update on user's authorized data only
            appUserOptional.get().setName(appUserToUpdated.getName());
            appUserOptional.get().setFirstName(appUserToUpdated.getFirstName());
            appUserOptional.get().setEmail(appUserToUpdated.getEmail());
            //update in database
            return appUserRepository.save(appUserOptional.get());
        } else {
            return null;
        }
    }
    @Override
    public AppUser updateAppUserForAdmin(AppUser appUserToUpdated) {

        // Admin can update all data from users/admin excepts  : creation & update date, uid, id
        Optional<AppUser> appUserOptional = appUserRepository.findById(appUserToUpdated.getId());

        if (appUserOptional.isPresent()) {
            // Update on admin's authorized data only
            appUserOptional.get().setName(appUserToUpdated.getName());
            appUserOptional.get().setFirstName(appUserToUpdated.getFirstName());
            appUserOptional.get().setEmail(appUserToUpdated.getEmail());
            appUserOptional.get().setAuthorities(appUserToUpdated.getAuthorities());
            appUserOptional.get().setUrlLinkSet(appUserToUpdated.getUrlLinkSet());
            //update in database
            return appUserRepository.save(appUserOptional.get());
        } else {
            return null;
        }
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
