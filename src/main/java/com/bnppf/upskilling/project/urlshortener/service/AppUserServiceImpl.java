package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {


    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    /**
     * Create AppUser
     * @param appUserToBeCreated
     * @return created AppUser
     */

    @Override
    public AppUser createAppUser(AppUser appUserToBeCreated) {
        return appUserRepository.save(appUserToBeCreated);
    }

    /**
     * Find all existing AppUser
     * @return List of all existing App users
     */
    @Override
    public List<AppUser> getAppUserList() {
        return this.appUserRepository.findAll();
    }

    /**
     * Find an Appuser by its UID
     * @param appUserUID
     * @return
     */
    @Override
    public Optional<AppUser> getAppUserByUID(String appUserUID) {
        return appUserRepository.findByUid(appUserUID);
    }

    /**
     * Update AppUser
     * @param appUserToUpdated
     * @return User updated
     */
    @Override
    public AppUser updateAppUser(AppUser appUserToUpdated) {

        if (appUserRepository.existsById(appUserToUpdated.getId()))
        {
            return appUserRepository.save(appUserToUpdated);
        } else {
            return null;
        }
    }

    /**
     * Delete AppUser from its Id
     * @param appUserIdToDelete
     * @return status boolean(true/false)
     */
    @Override
    public boolean deleteAppUser(Long appUserIdToDelete) {
        if (appUserRepository.existsById(appUserIdToDelete)){
            appUserRepository.deleteById(appUserIdToDelete);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete a List of AppUser by their Ids
     * @param appUserIdListToDelete
     * @return status boolean(true/false) for each AppUser considered
     */
    @Override
    public List<Boolean> deleteAppUserList(List<Long> appUserIdListToDelete) {

        List<Boolean> resList = null;

        for (int i = 0; i < appUserIdListToDelete.size(); i++) {
            if (appUserRepository.existsById(appUserIdListToDelete.get(i))) {
                appUserRepository.deleteById(appUserIdListToDelete.get(i));
                resList.add(true);
            } else {
                resList.add(false);
            }
        }
        return resList;
    }
}
