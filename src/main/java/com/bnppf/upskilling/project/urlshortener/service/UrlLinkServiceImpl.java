package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.UrlLinkRepository;
import com.bnppf.upskilling.project.urlshortener.vm.KeyCalculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.URL;
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

    /**
     * Injection of Repository inside Service Constructor
     * @param urllinkRepository
     */
    public UrlLinkServiceImpl(UrlLinkRepository urllinkRepository){
        this.urllinkRepository = urllinkRepository;
    }



    public UrlLink createUrlForGuest(URL urlToBeCreated) {

        /**
         * Create URL shortKey
         */
        String generatedKey = KeyCalculation.URLKeyCalculation();
        Optional<UrlLink> urlChecking = urllinkRepository.findOneByUrlShortKey(generatedKey);

        /**
         * Check if URL shortKey already exist : if so, keep on trying to find
         * new URL shortKey
         */
        while(urlChecking.isPresent())
        {
            generatedKey = KeyCalculation.URLKeyCalculation();
            urlChecking = urllinkRepository.findOneByUrlShortKey(generatedKey);
        }

        /**
         * Populate all necessary attributes for URL creation : case GUEST
         */

        UrlLink urlLinkToCreate = new UrlLink();
            urlLinkToCreate.setUrlLong(urlToBeCreated);
            urlLinkToCreate.setUrlShortKey(generatedKey);
            urlLinkToCreate.setClickNumber(0D);
            urlLinkToCreate.setMaxClickNumber(MAXCLICKNUMBER);
            urlLinkToCreate.setExpirationDate(LocalDateTime.now().plusDays(10));
            urlLinkToCreate.setCreationDate(LocalDateTime.now());
            urlLinkToCreate.setUpdateDate(LocalDateTime.now());

            return urllinkRepository.save(urlLinkToCreate);

    }

//    /**
//     * Create a new URL link attached to one user
//     * @param urlLinkToBeCreated
//     * @return creation status
//     */
//    @Override
//    public UrlLink createUrl(UrlLink urlLinkToBeCreated) {
//        return urllinkRepository.save(urlLinkToBeCreated);
//    }

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

    public List<UrlLink> getAllUrlLinks(Integer pageNo, Integer pageSize, String sortBy){

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<UrlLink> pagedResult = urllinkRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<UrlLink>();
        }
    }

    /**
     * Update one UrlLink on defined criteria
     * @param urlLinkToUpdate
     * @return
     */
    @Override
    public UrlLink updateUrlLink(UrlLink urlLinkToUpdate) {
        if (urllinkRepository.existsById(urlLinkToUpdate.getId())){
            return urllinkRepository.save(urlLinkToUpdate);
        } else {
            return null;
        }
    }

    /**
     * Update a list of UrlLink on different criteria (hors MVP)
     * @param urlLinkList
     * @return
     */
    @Override
    public List<UrlLink> updateUrlLinkList(List<UrlLink> urlLinkList) {

        List<UrlLink> urlLinkListUpdated = new ArrayList<>();

        for (int i = 0; i< urlLinkList.size(); i++) {

            if (urllinkRepository.existsById(urlLinkList.get(i).getId())){
                urllinkRepository.save(urlLinkList.get(i));
            }
            urlLinkListUpdated.add(urlLinkList.get(i));
        }
        return urlLinkListUpdated;
    }

    @Override
    public boolean deleteUrlLink(Long urlLinkIdToBeDeleted) {
        if (urllinkRepository.existsById(urlLinkIdToBeDeleted)){
            urllinkRepository.deleteById(urlLinkIdToBeDeleted);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Boolean> deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted) {

        List<Boolean> resList = null;

        for (int i = 0; i < urlLinkIdListToBeDeleted.size(); i++) {
            if (urllinkRepository.existsById(urlLinkIdListToBeDeleted.get(i))) {
                urllinkRepository.deleteById(urlLinkIdListToBeDeleted.get(i));
                resList.add(true);
            } else {
                resList.add(false);
            }
        }
        return resList;
    }
}
