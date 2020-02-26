package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.vm.UrlString;

import java.util.List;

public interface UrlLinkService {


//    /**
//     * Creation a a new URL link
//     * @param urlLinkToBeCreated
//     * @return urllink created
//     */
//    UrlLink createUrl(UrlLink urlLinkToBeCreated);

    /**
     * Creation of a new URLLink for GUEST
     * @param urlToBeCreated
     * @return URLLink created
     */
    UrlLink createUrlLink(UrlString urlToBeCreated);

    /**
     * CREATION OF NEW URLLINK FOR USERS
     * @param urlToBeCreated
     * @return
     */
    UrlLink createUrlForUser(UrlString urlToBeCreated);

//    /**
//     * Get all URL link
//     * @return url List
//     */
//    List<UrlLink> getUrlList();

//    /**
//     * Get UrlLink list for a specific Application User sorted by ASC creation date
//     * @param appUser
//     * @return UrlLink list created by the specific AppUser sorted by ascending creation date
//     */
//    List<UrlLink> getUrlListForOneAppUserSortedByAscCreationDate (AppUser appUser);
//
//
//    // Hors MVP -----------------------------------
//    /**
//     * Get list of UrlLink for a specific AppUser from its title
//     * @param appUser
//     * @return urlLink list of a user sorted by title
//     */
//    List<UrlLink> getUrlListForOneAppUserSortedByTitle(AppUser appUser);
//
//
//    // Hors MVP -----------------------------------
//    /**
//     * Get list of UrlLink from its expiration date
//     * @param appUser
//     * @return url list sorted by expiration date
//     */
//    List<UrlLink> getUrlListForOneAppUserSortedByExpirationdate(AppUser appUser);
//
//    /**
//     * Get UrlLink List sorted by click number
//     * @param appUser
//     * @return url List sorted by lick number
//     */
//    List<UrlLink> getUrlListForOneAppUserSortedByClickNumber(AppUser appUser);


    /**
     * get All URLLinks sorted by criteria: for ADMIN only
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    List<UrlLink> getAllUrlLinks (Integer pageNo, Integer pageSize, String sortBy);

    /**
     * Update one UrlLink for one user
     * @param urlLinkToUpdate
     * @return urlLink updated
     */
    UrlLink updateUrlLink(UrlLink urlLinkToUpdate);


    /**
     * Update a list of Url link for one App user
     * @param urlLinkList
     * @return UrlLink list updated
     */
    List<UrlLink> updateUrlLinkList(List<UrlLink> urlLinkList);

    /**
     * Delete one UrlLink by Id
     * @param urlLinkIdToBeDeleted
     * @return status of delete
     */
    boolean deleteUrlLink(Long urlLinkIdToBeDeleted);

    /**
     * Delete a list of Url
     * @param urlLinkIdListToBeDeleted
     * @return status of delete
     */
    List<Boolean> deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted);
}
