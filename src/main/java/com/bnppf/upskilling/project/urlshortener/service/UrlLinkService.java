package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;

import java.util.Date;
import java.util.Set;

public interface UrlLinkService {


    /**
     * Creation a a new URL link
     * @param urlLinkToBeCreated
     * @return urllink created
     */
    UrlLink createUrl(UrlLink urlLinkToBeCreated);


    /**
     * Get all URL link
     * @return url Set
     */
    Set<UrlLink> getUrlSet();

    /**
     * Get UrlLink set for a specific Application User
     * @param AppUserId
     * @return UrlLink set created by the specific AppUser sorted by ascending creation date
     */
    Set<UrlLink> getUrlSetForOneAppUserSortedByAscCreationDate (Long AppUserId);


    // Hors MVP -----------------------------------
    /**
     * Get set of UrlLink from its title
     * @param urlLinkTitle
     * @return urlLink set sorted by title
     */
    Set<UrlLink> getUrlSetSortedByTitle(String urlLinkTitle);


    // Hors MVP -----------------------------------
    /**
     * Get set of UrlLink from its creation date
     * @param urlLinkCreationDate
     * @return url set sorted by creation date
     */
    Set<UrlLink> getUrlSetSortedByCreationdate(Date urlLinkCreationDate);


    // Hors MVP -----------------------------------
    /**
     * Get set of UrlLink from its expiration date
     * @param urlLinkExpirationDate
     * @return url set sorted by expiration date
     */
    Set<UrlLink> getUrlSetSortedByExpirationdate(Date urlLinkExpirationDate);

    /**
     * Get UrlLink set sorted by click number
     * @param clickNumber
     * @return url set sorted by lick number
     */
    Set<UrlLink> getUrlSetSortedByClickNumber(Double clickNumber);

    /**
     * Update one UrlLink for one user
     * @param urlLinkToUpdate
     * @return urlLink updated
     */
    UrlLink updateUrlLink(UrlLink urlLinkToUpdate);


    /**
     * Update a set of Url link for one App user
     * @param urlLinkSet
     * @return UrlLink set updated
     */
    Set<UrlLink> updateUrlLinkSet(Set<UrlLink> urlLinkSet);

    /**
     * Delete one UrlLink by Id
     * @param urlLinkIdToBeDeleted
     * @return status of delete
     */
    boolean deleteUrlLink(Long urlLinkIdToBeDeleted);

    /**
     * Delete a list of Url
     * @param urlLinkIdSetToBeDeleted
     * @return status of delete
     */
    boolean deleteUrlLinkSet(Set<Long> urlLinkIdSetToBeDeleted);
}
