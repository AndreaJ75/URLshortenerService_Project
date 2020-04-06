package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import com.bnppf.upskilling.project.urlshortener.repository.UrlLinkRepository;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import javassist.runtime.Desc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlLinkServiceImpl implements UrlLinkService {


    private final Double MAXCLICKNUMBER = Double.MAX_VALUE;
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
        urlLinkToBeCreated.setMaxClickNumber(urlToBeCreated.getMaxClickNumber());

        /**
         * Feed provided Url Password
         */
        urlLinkToBeCreated.setUrlPassword(urlToBeCreated.getAppPassword());
        /**
         * Feed provided Expiration Date
         */
        urlLinkToBeCreated.setExpirationDate(urlToBeCreated.getExpirationDate());

        /**
         * Feed Connected AppUser using its Login = UID
         */
        String loginConnected = SecurityUtils.getCurrentUserLogin();
        Optional<AppUser> userOptional = appUserRepository.findAppUserByUid(loginConnected);

//      Check LDAP file to take required data?
//        AppUser userToBeCreated = new AppUser(data required from LDAP?);
//        if (!userOptional.isPresent()) {
//            appUserService.createAppUser(userToBeCreated);
//        }

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

    /**
     * Find all UrlLink for all users => Admin functionnality
     * @return List of UrlLink of all users
     */
    @Override
    public Page<UrlLink> getUrlListAllSorted(Pageable pageable) {
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
            System.out.println(userOptional.get());
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
