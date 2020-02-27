package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.configuration.Utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.AppUserRepository;
import com.bnppf.upskilling.project.urlshortener.repository.UrlLinkRepository;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    /**
     * Injection of Repository inside Service Constructor
     *
     * @param urllinkRepository
     */
    public UrlLinkServiceImpl(UrlLinkRepository urllinkRepository, AppUserRepository appUserRepository) {
        this.urllinkRepository = urllinkRepository;
        this.appUserRepository = appUserRepository;
    }


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
        Optional<AppUser> userOptional = appUserRepository.findByUid(loginConnected);

        // code ci-dessous idem code d'après
//        if (userOptional.isPresent()) {
//            urlLinkToBeCreated.setAppUser(userOptional.get());
//        }

        userOptional.ifPresent(urlLinkToBeCreated::setAppUser);

        return urllinkRepository.save(urlLinkToBeCreated);
    }

    /**
     * Get URLLong from Short URL link
     */

    public Optional<UrlLink> getUrlLongFromShortUrl(String urlShort) {
        return urllinkRepository.findOneByUrlShortKey(urlShort);
    }

//    /**
//     * Find all UrlLink for all users => Admin functionnality
//     * @return List of UrlLink of all users
//     */
//    @Override
//    public List<UrlLink> getUrlList() {
//        return urllinkRepository.findAll();
//    }

//    /**
//     * Find all urllink for a dedicated user sorted by Asc creationDate
//     * @param appUser
//     * @return List of UrlLink for one usersorted by Asc creationDate
//     */
//    @Override
//    public List<UrlLink> getUrlListForOneAppUserSortedByAscCreationDate(AppUser appUser) {
//        return urllinkRepository.findByAppUserOrderByCreationDateAsc(appUser);
//    }

//
//
//    // Hors MVP => to be implemented
//    @Override
//    public List<UrlLink> getUrlListForOneAppUserSortedByTitle(AppUser appUser) {
//        return null;
//    }

//    /**
//     * Find a list of UrlLink for a dedicated user sorted by Asc expiration date
//     * @param appUser
//     * @return List of UrlLink for a dedicated user sorted by Asc expiration date
//     */
//    @Override
//    public List<UrlLink> getUrlListForOneAppUserSortedByExpirationdate(AppUser appUser) {
//        return urllinkRepository.findByAppUserOrderByExpirationDateAsc(appUser);
//    }

//    /**
//     * Find list of UrlLink for a dedicated user sorted by Descending click Number
//     * @param appUser
//     * @return list of UrlLink for a dedicated user sorted by Descending click Number
//     */
//    @Override
//    public List<UrlLink> getUrlListForOneAppUserSortedByClickNumber(AppUser appUser) {
//        return urllinkRepository.findByAppUserOrderByClickNumberDesc(appUser);
//    }

//    public List<UrlLink> getAllUrlLinks(String sortBy){
//
//        Pageable paging = PageRequest.of(0, 2, Sort.by(sortBy));
//
//        Page<UrlLink> pagedResult = urllinkRepository.findAllByCreationDateOrClickNumberOrExpirationDate(paging);
//
//        if(pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<UrlLink>();
//        }
//    }

    /**
     * Update one UrlLink on defined criteria
     *
     * @param urlLinkToUpdate
     * @return
     */
    @Override
    public UrlLink updateUrlLink(UrlLink urlLinkToUpdate) {
        if (urllinkRepository.existsById(urlLinkToUpdate.getId())) {
            return urllinkRepository.save(urlLinkToUpdate);
        } else {
            return null;
        }
    }

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
