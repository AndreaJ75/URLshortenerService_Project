package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.repository.UrllinkRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UrlLinkServiceImpl implements UrlLinkService {

    /**
     * Instance of Repository declaration
     */
    private UrllinkRepository urllinkRepository;

    /**
     *
     * @param urllinkRepository
     */
    public UrlLinkServiceImpl(UrllinkRepository urllinkRepository){
        this.urllinkRepository = urllinkRepository;
    }

    @Override
    public UrlLink createUrl(UrlLink urlLinkToBeCreated) {

        return null;
    }

    @Override
    public List<UrlLink> getUrlList() {
        return null;
    }

    @Override
    public List<UrlLink> getUrlListForOneAppUserSortedByAscCreationDate(Long AppUserId) {
        return null;
    }

    @Override
    public List<UrlLink> getUrlListSortedByTitle(String urlLinkTitle) {
        return null;
    }

    @Override
    public List<UrlLink> getUrlListSortedByCreationdate(Date urlLinkCreationDate) {
        return null;
    }

    @Override
    public List<UrlLink> getUrlListSortedByExpirationdate(Date urlLinkExpirationDate) {
        return null;
    }

    @Override
    public List<UrlLink> getUrlListSortedByClickNumber(Double clickNumber) {
        return null;
    }

    @Override
    public UrlLink updateUrlLink(UrlLink urlLinkToUpdate) {
        return null;
    }

    @Override
    public List<UrlLink> updateUrlLinkList(List<UrlLink> urlLinkList) {
        return null;
    }

    @Override
    public boolean deleteUrlLink(Long urlLinkIdToBeDeleted) {
        return false;
    }

    @Override
    public boolean deleteUrlLinkList(List<Long> urlLinkIdListToBeDeleted) {
        return false;
    }
}
