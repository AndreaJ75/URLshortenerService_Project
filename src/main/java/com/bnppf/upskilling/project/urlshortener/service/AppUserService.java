package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;

import java.util.List;
import java.util.Set;

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
    Set<AppUser> getAppUserSet();

    /**
     * Get user from Application user by its UID
     * @param appUserUID
     * @return appUser
     */
    AppUser getAppUserByUID(String appUserUID);


    /**
     * Get Application user by its Name
     * @param appUserName
     * @return appUser
     */
    AppUser getAppUserByName(String appUserName);

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
     * Delete UserSet from their id
     * @param appUserIdSetToDelete
     * @return resulted status return by delete
     */
    boolean deleteAppUserSet(Long appUserIdSetToDelete);


}
