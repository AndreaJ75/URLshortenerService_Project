package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UrlLinkService {

    /**
     * Creation of a new URLLink for GUEST
     * @param urlToBeCreated
     * @return URLLink created
     */
    UrlLink createUrlLink(UrlFeedLink urlToBeCreated);

    /**
     * CREATION OF NEW URLLINK FOR USERS
     * @param urlToBeCreated
     * @return
     */
    UrlLink createUrlForUser(UrlFeedLink urlToBeCreated);

    /**
     * Get URLLink from urlId
     */
    Optional<UrlLink> getUrlLinkfromUrlId(Long urlId);


    /**
     * Get URLFromShortURL
     */
    Optional<UrlLink> getUrlLongFromShortUrl(String shortUrl);


    /**
     * GET all UrlLinks created by a User Sorted by default on urlLong ASC.
     * @param pageable
     * @return sorted UrlLinks (on urlLong ASC)) created by related user
     */
    Page <UrlLink> getUrlLinksSortedBySortCriteriaandOrder(Pageable pageable);

    /**
     * Get all URL link sorted (for ADMIN users only)
     * @return url List
     */
    Page<UrlLink> getUrlListAllSorted(Pageable pageable);

    /**
     * Update one UrlFeedLink for one user
     * @param urlFeedLinkToUpdate
     * @return urlLink updated
     */
    UrlLink updateUrlFeedLink(UrlFeedLink urlFeedLinkToUpdate);

    /**
     * Update one UrlFeedLink for one user
     * @param urlLinkToUpdate
     * @return urlLink updated
     */
    UrlLink updateUrlLink(UrlLink urlLinkToUpdate);

    /**
     * Delete one UrlLink by Id
     * @param urlLinkIdToBeDeleted
     * @return status of delete
     */
    void deleteUrlLink(Long urlLinkIdToBeDeleted);

    /**
     * Delete a list of Url
     * @param urlLinkIdListToBeDeleted
     * @return status of delete
     */
    void deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted);
}
