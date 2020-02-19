package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;

import java.util.Date;
import java.util.List;
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
     * @return url List
     */
    List<UrlLink> getUrlList();

    /**
     * Get UrlLink list for a specific Application User
     * @param AppUserId
     * @return UrlLink list created by the specific AppUser sorted by ascending creation date
     */
    List<UrlLink> getUrlListForOneAppUserSortedByAscCreationDate (Long AppUserId);


    // Hors MVP -----------------------------------
    /**
     * Get list of UrlLink from its title
     * @param urlLinkTitle
     * @return urlLink list sorted by title
     */
    List<UrlLink> getUrlListSortedByTitle(String urlLinkTitle);


    // Hors MVP -----------------------------------
    /**
     * Get list of UrlLink from its creation date
     * @param urlLinkCreationDate
     * @return url list sorted by creation date
     */
    List<UrlLink> getUrlListSortedByCreationdate(Date urlLinkCreationDate);


    // Hors MVP -----------------------------------
    /**
     * Get list of UrlLink from its expiration date
     * @param urlLinkExpirationDate
     * @return url list sorted by expiration date
     */
    List<UrlLink> getUrlListSortedByExpirationdate(Date urlLinkExpirationDate);

    /**
     * Get UrlLink List sorted by click number
     * @param clickNumber
     * @return url List sorted by lick number
     */
    List<UrlLink> getUrlListSortedByClickNumber(Double clickNumber);

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
    boolean deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted);
}
