package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService  {

    /**
     * Creation of an Application user
     * @param appUserToBeCreated
     * @return appUser
     */
    AppUser createAppUser(AppUser appUserToBeCreated);

    /**
     * Get list of all existing registered application users :
     * include ADMIN and USER only (not GUEST)
     * @return
     */
    List<AppUser> getAppUserList();

    /**
     * Get user from Application user by its UID
     * @param appUserUID
     * @return appUser
     */
    Optional<AppUser> getAppUserByUID(String appUserUID);

    /**
     * Update on Application user
     * @param appUserToUpdated
     * @return AppUser updated
     */

    AppUser updateAppUser(AppUser appUserToUpdated);


    /**
     * Delete user from its id
     * @param appUserIdToDelete
     * @return resulted status return by delete
     */
    boolean deleteAppUser(Long appUserIdToDelete);

    /**
     * Delete UserList from their id
     * @param appUserIdListToDelete
     * @return resulted status return by delete for each AppUser
     */
    List<Boolean> deleteAppUserList(List<Long> appUserIdListToDelete);


}
