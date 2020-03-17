package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/urllinks")
public class UrlLinkController {

    /**
     * Declaration du service pour access aux données en Base de données via repository
     */
    private UrlLinkService urlLinkService;

    public UrlLinkController(UrlLinkService urlLinkService) { this.urlLinkService = urlLinkService;  }


    /**
     * CREATE URLLink for GUEST
     * @param urlLinkToBeCreated
     * @return
     */
    //=> OK testé
    @PostMapping("/guest/post")
    public ResponseEntity<UrlLink> createUrlLink(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        return ResponseEntity.ok(urlLinkService.createUrlLink(urlLinkToBeCreated));
    }


    /**
     * CREATE URLLink for USER
     * @param urlLinkToBeCreated
     * @return
     */
    //=> OK testé
    @PostMapping("/user/post")
    public ResponseEntity<UrlLink> createUrlForUser(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        /**
         * Check if token is OK for considered User
         * If so, create the corresponding URLLink -> createUrlLinkFor User
         * Else sout "Wrong token"
         */
        return ResponseEntity.ok(urlLinkService.createUrlForUser(urlLinkToBeCreated));
    }

    //=> OK testé
    @GetMapping("/user/getsorted")
    public ResponseEntity<Page<UrlLink>> getUrlLinksSortedBySortCriteriaandOrder(Pageable pageable) {
        return ResponseEntity.ok(urlLinkService.getUrlLinksSortedBySortCriteriaandOrder(pageable));
    }

    // Get all Urls from all users (for Admin only)
    //=> OK testé
    @GetMapping("/admin/urlall")
    public ResponseEntity<Page<UrlLink>> getUrlLinkList (Pageable pageable ){
        return ResponseEntity.ok(urlLinkService.getUrlListAllSorted(pageable));
    }

    //=> A tester (voir si limitation des MAJ user uniquement sur 3 champs considérés)
    @PutMapping("/user/put")
    public ResponseEntity<UrlLink> updateAppUserUrl(@RequestBody UrlLink urlLink){

        UrlLink urlLinkToUpdate = new UrlLink();
        urlLinkToUpdate.setMaxClickNumber(urlLink.getMaxClickNumber());
        urlLinkToUpdate.setExpirationDate(urlLink.getExpirationDate());
        urlLinkToUpdate.setUrlPassword(urlLink.getUrlPassword());
        urlLinkToUpdate.setUpdateDate(LocalDateTime.now());

        urlLinkService.updateUrlLink(urlLinkToUpdate);

        if (urlLinkToUpdate != null) {
            return ResponseEntity.ok(urlLinkToUpdate);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //=> OK testé
    @DeleteMapping("/user/deleteurl")
    public void deleteAppUserUrlLink(@PathParam("urlId") Long urlId){
        urlLinkService.deleteUrlLink(urlId);
    }

    //=> A tester
    @DeleteMapping("/user/deleteurls")
    public void deleteAppUserUrlLinkList(@PathParam("urlIdList") List<Long> urlIdList) {
        urlLinkService.deleteUrlLinkList(urlIdList);
    }

}
