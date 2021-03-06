package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface AppUserService  {

    /**
     * Creation of an Application user
     * @param appUserToBeCreated
     * @return appUser
     */
    AppUser createOrUpdateAppUser(AppUser appUserToBeCreated);

    /**
     * Update AppUser Authoritylevel
     * @param appUserId
     * @return
     */
    AppUser createAppUserAuthority(Long appUserId);

    /**
     * Remove AppUser AuthorityLevel
     * @param appUserId
     */
    AppUser removeAppUserAuthority(Long appUserId);
    /**
     * Get list of all existing registered application users :
     * include ADMIN and USER only (not GUEST)
     * @return
     */
    Page<AppUser> getAppUserList(Pageable pageable);

    /**
     * Get AppUser filtered on name criteria
     * @param pageable
     * @return
     */
    Page<AppUser> getAppUsersFilteredsOnName(String name, Pageable pageable);
    /**
     * Get List of all appuser's roles
     * @param appUserLogin
     * @return
     */
    List<String> getAppUserRoles(String appUserLogin);

    /**
     * Get user from Application user by its UID
     * @param appUserUID
     * @return appUser
     */
    Optional<AppUser> getAppUserByUID(String appUserUID);

    /**
     * Delete user from its id
     * @param appUserIdToDelete
     * @return resulted status return by delete
     */
    void deleteAppUser(Long appUserIdToDelete);

    /**
     * Delete UserList from their id
     * @param appUserIdListToDelete
     * @return resulted status return by delete for each AppUser
     */
    void deleteAppUserList(List<Long> appUserIdListToDelete);

}
