package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.UrlLinkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UrlLinkServiceImpl implements UrlLinkService {

    /**
     * Instance of Repository declaration
     */
    private UrlLinkRepository urllinkRepository;

    /**
     *
     * @param urllinkRepository
     */
    public UrlLinkServiceImpl(UrlLinkRepository urllinkRepository){
        this.urllinkRepository = urllinkRepository;
    }

    /**
     * Create a new URL link attached to one user
     * @param urlLinkToBeCreated
     * @return creation status
     */
    @Override
    public UrlLink createUrl(UrlLink urlLinkToBeCreated) {
        return urllinkRepository.save(urlLinkToBeCreated);
    }

    /**
     * Find all UrlLink for all users => Admin functionnality
     * @return List of UrlLink of all users
     */
    @Override
    public List<UrlLink> getUrlList() {
        return urllinkRepository.findAll();
    }

    /**
     * Find all urllink for a dedicated user sorted by Asc creationDate
     * @param appUser
     * @return List of UrlLink for one usersorted by Asc creationDate
     */
    @Override
    public List<UrlLink> getUrlListForOneAppUserSortedByAscCreationDate(AppUser appUser) {
        return urllinkRepository.findByAppUserOrderByCreationDateAsc(appUser);
    }



    // Hors MVP => to be implemented
    @Override
    public List<UrlLink> getUrlListForOneAppUserSortedByTitle(AppUser appUser) {
        return null;
    }

    /**
     * Find a list of UrlLink for a dedicated user sorted by Asc expiration date
     * @param appUser
     * @return List of UrlLink for a dedicated user sorted by Asc expiration date
     */
    @Override
    public List<UrlLink> getUrlListForOneAppUserSortedByExpirationdate(AppUser appUser) {
        return urllinkRepository.findByAppUserOrderByExpirationDateAsc(appUser);
    }

    /**
     * Find list of UrlLink for a dedicated user sorted by Descending click Number
     * @param appUser
     * @return list of UrlLink for a dedicated user sorted by Descending click Number
     */
    @Override
    public List<UrlLink> getUrlListForOneAppUserSortedByClickNumber(AppUser appUser) {
        return urllinkRepository.findByAppUserOrderByClickNumberDesc(appUser);
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
