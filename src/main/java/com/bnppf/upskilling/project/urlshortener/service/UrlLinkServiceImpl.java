package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import com.bnppf.upskilling.project.urlshortener.repository.UrlLinkRepository;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlLinkServiceImpl implements UrlLinkService {


    private final Double MAXCLICKNUMBER = 50000D;
    /**
     * Instance of Repository declaration
     */
    private UrlLinkRepository urllinkRepository;

    private AppUserRepository appUserRepository;
    private AppUserService appUserService;

    /**
     * Injection of Repository inside Service Constructor
     *
     * @param urllinkRepository
     */
    public UrlLinkServiceImpl(UrlLinkRepository urllinkRepository,
                              AppUserRepository appUserRepository,
                              AppUserService appUserService) {
        this.urllinkRepository = urllinkRepository;
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    // ********************************************************************************
    // ***************              CREATE                     ************************
    // ********************************************************************************
    /**
     * Creation of UrlLink Common part (between Guest ad user)
     * @param urlToBeCreated
     * @return urlCreated
     */

    public UrlLink createCommon(UrlFeedLink urlToBeCreated) {
        /**
         * Create URL shortKey
         */
        String generatedKey = KeyCalculation.URLKeyCalculation();
        Optional<UrlLink> urlChecking = urllinkRepository.findOneByUrlShortKey(generatedKey);

        /**
         * Check if URL shortKey already exist : if so, keep on trying to find
         * new URL shortKey
         */
        while (urlChecking.isPresent()) {
            generatedKey = KeyCalculation.URLKeyCalculation();
            urlChecking = urllinkRepository.findOneByUrlShortKey(generatedKey);
        }

        /**
         * Populate all necessary attributes for URL creation : case GUEST
         */

        UrlLink urlLinkToCreate = new UrlLink();
        urlLinkToCreate.setUrlLong(urlToBeCreated.getUrlLong());
        urlLinkToCreate.setUrlShortKey(generatedKey);
        urlLinkToCreate.setClickNumber(0D);
        urlLinkToCreate.setMaxClickNumber(MAXCLICKNUMBER);
        urlLinkToCreate.setExpirationDate(LocalDateTime.now().plusDays(10));
        urlLinkToCreate.setCreationDate(LocalDateTime.now());
        urlLinkToCreate.setUpdateDate(LocalDateTime.now());

        return urlLinkToCreate;
    }

    /**
     * CREATE URLLINK for GUEST
     *
     * @param urlToBeCreated
     * @return created UrlLink
     */
    public UrlLink createUrlLink(UrlFeedLink urlToBeCreated) {

        UrlLink urlLinkToBeCreated = this.createCommon(urlToBeCreated);

        return urllinkRepository.save(urlLinkToBeCreated);

    }

    /**
     * CREATE URLLINK for USERS
     *
     * @param urlToBeCreated
     * @return created UrlLink
     */
    public UrlLink createUrlForUser(UrlFeedLink urlToBeCreated) {

        UrlLink urlLinkToBeCreated = this.createCommon(urlToBeCreated);

        /**
         * Feed max click number provided by user
         */
        if (!(urlToBeCreated.getMaxClickNumber() == null)) {
            urlLinkToBeCreated.setMaxClickNumber(urlToBeCreated.getMaxClickNumber());
        }

        /**
         * Feed provided Url Password
         */
        urlLinkToBeCreated.setUrlPassword(urlToBeCreated.getAppPassword());
        /**
         * Feed provided Expiration Date (if provided with today date, set to todayday +1)
         */
        if (LocalDateTime.now().getDayOfYear() == urlToBeCreated.getExpirationDate().getDayOfYear()
                && LocalDateTime.now().getYear() == urlToBeCreated.getExpirationDate().getYear()){
            urlLinkToBeCreated.setExpirationDate(LocalDateTime.now().plusDays(1));
        } else {
            urlLinkToBeCreated.setExpirationDate(urlToBeCreated.getExpirationDate());
        }

        /**
         * Feed Connected AppUser using its Login = UID
         */
        String loginConnected = SecurityUtils.getCurrentUserLogin();
        Optional<AppUser> userOptional = appUserRepository.findAppUserByUid(loginConnected);

        // code ci-dessous idem code d'après
//        if (userOptional.isPresent()) {
//            urlLinkToBeCreated.setAppUser(userOptional.get());
//        }

        userOptional.ifPresent(urlLinkToBeCreated::setAppUser);

        return urllinkRepository.save(urlLinkToBeCreated);
    }
    // ********************************************************************************
    // ***************              READ                       ************************
    // ********************************************************************************

    /**
     * Get urlLink from its Id
     */
    @Override
    public Optional<UrlLink> getUrlLinkfromUrlId(Long urlId) {

        return urllinkRepository.findById(urlId);
    }

    /**
     * Get URLLong from Short URL link
     */
    @Override
    public Optional<UrlLink> getUrlLongFromShortUrl(String urlShort) {
        return urllinkRepository.findOneByUrlShortKey(urlShort);
    }

    // Get urlLinks List related to one user
    @Override
    public Page<UrlLink> getUrlLinksToDeleteForOneUser(Long appUserIdToDelete,
                                                       Pageable pageable) {

        Optional<AppUser> userOptional = appUserRepository.findById(appUserIdToDelete);

        if (userOptional.isPresent()) {
            return urllinkRepository.findAllByAppUser(userOptional.get(), pageable);
        } else {
            return null;
        }
    }

//    @Override
//    public Page<UrlLink> getUrlLinkFilteredOnShorkeyForOneUser(String urlShortKey,
//                                                               Pageable pageable) {
//
//        return
//    }
    @Override
    public Page<UrlLink> getUrlLinkFilteredOnAppUserForAdmin(String firstName,
                                                             String name,
                                                             Pageable pageable){

        List<AppUser> appUsers = appUserRepository
                .findByFirstNameAndName(firstName, name);

        Page<UrlLink> urlLinkPage = null;
        for (AppUser appUser: appUsers) {
            urlLinkPage = urllinkRepository.findAllByAppUser(appUser, pageable);
        }
        return urlLinkPage;

    }
    /**
     * Find all UrlLink for all users => Admin functionnality
     * @return List of UrlLink of all users
     */
    @Override
    public Page<UrlLink> getUrlPageAllSorted(Pageable pageable) {

        return urllinkRepository.findAll(pageable);
    }

    @Override
    public Page<UrlLink> getUrlLinksSortedBySortCriteriaandOrder(Pageable pageable) {
         /**
         //* Feed Connected AppUser using its Login = UID
         //*/
        String loginConnected = SecurityUtils.getCurrentUserLogin();
        Optional<AppUser> userOptional = appUserRepository.findAppUserByUid(loginConnected);

        if (userOptional.isPresent()) {
            return urllinkRepository.findAllByAppUser(userOptional.get(), pageable);
        } else {
            return null;
        }
    }

    // ********************************************************************************
    // ***************              UPDATE                     ************************
    // ********************************************************************************

    /**
     * Update one UrlLink on defined criteria
     * @param urlFeedLinkToUpdate
     * @return
     */
    @Override
    public UrlLink updateUrlFeedLink(UrlFeedLink urlFeedLinkToUpdate) {

        // Retrieve urlLink data if exists
        Optional<UrlLink> urlLinkOptional = urllinkRepository.findById(urlFeedLinkToUpdate.getId());

        if (urlLinkOptional.isPresent()) {
            // update only authorized (for update) urlLink data
            urlLinkOptional.get().setMaxClickNumber(urlFeedLinkToUpdate.getMaxClickNumber());
            urlLinkOptional.get().setExpirationDate(urlFeedLinkToUpdate.getExpirationDate());
            urlLinkOptional.get().setUrlPassword(urlFeedLinkToUpdate.getAppPassword());
            urlLinkOptional.get().setUpdateDate(LocalDateTime.now());
            // update inside database
            return urllinkRepository.save(urlLinkOptional.get());
        } else {
            return null;
        }
    }

    /**
     * Update one UrlLink on defined criteria
     * @param urlLinkToUpdate
     * @return
     */
    @Override
    public UrlLink updateUrlLink(UrlLink urlLinkToUpdate) {

        // Retrieve urlLink data if exists
        Optional<UrlLink> urlLinkOptional = urllinkRepository.findById(urlLinkToUpdate.getId());

        if (urlLinkOptional.isPresent()) {
            // update only authorized (for update) urlLink data
            urlLinkOptional.get().setMaxClickNumber(urlLinkToUpdate.getMaxClickNumber());
            urlLinkOptional.get().setExpirationDate(urlLinkToUpdate.getExpirationDate());
            urlLinkOptional.get().setUrlPassword(urlLinkToUpdate.getUrlPassword());
            urlLinkOptional.get().setUpdateDate(LocalDateTime.now());
            // update inside database
            return urllinkRepository.save(urlLinkOptional.get());
        } else {
            return null;
        }
    }

    // ********************************************************************************
    // ***************              DELETE                     ************************
    // ********************************************************************************

    @Override
    public void deleteUrlLink(Long urlLinkIdToBeDeleted) {
        urllinkRepository.deleteById(urlLinkIdToBeDeleted);
    }

    @Override
    public void deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted) {
        for (Long id : urlLinkIdListToBeDeleted) {
            urllinkRepository.deleteById(id);
        }
    }
}
