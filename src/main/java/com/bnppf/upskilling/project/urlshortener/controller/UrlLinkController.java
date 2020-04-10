package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/urlLinks")
@CrossOrigin("*")
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

    @PostMapping("/guest")
    public ResponseEntity<UrlLink> createUrlLink(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        return ResponseEntity.ok(urlLinkService.createUrlLink(urlLinkToBeCreated));
    }

    /**
     * CREATE URLLink for USER
     * @param urlLinkToBeCreated
     * @return
     */

    @PostMapping("/user")
    public ResponseEntity<UrlLink> createUrlForUser(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        /**
         * Check if token is OK for considered User
         * If so, create the corresponding URLLink -> createUrlLinkFor User
         * Else sout "Wrong token"
         */
        return ResponseEntity.ok(urlLinkService.createUrlForUser(urlLinkToBeCreated));
    }


    @GetMapping("/user/getsorted")
    @CrossOrigin("*")
    public ResponseEntity<Page<UrlLink>> getUrlLinksSortedBySortCriteriaandOrder(Pageable pageable) {
        return ResponseEntity.ok(urlLinkService.getUrlLinksSortedBySortCriteriaandOrder(pageable));
    }

    // Get all Urls from all users (for Admin only)

    @GetMapping("/admin/urlsall")
    public ResponseEntity<Page<UrlLink>> getUrlLinkList (Pageable pageable ){
        return ResponseEntity.ok(urlLinkService.getUrlListAllSorted(pageable));
    }

    // Update only allowed on 3 attributes (check in service)
    @PutMapping("/user")
    public ResponseEntity<UrlLink> updateAppUserUrl(@RequestBody UrlLink urlLink){

        Optional<UrlLink> urlLinkOptional = urlLinkService.getUrlLinkfromUrlId(urlLink.getId());
        if (urlLinkOptional.isPresent())  {
            return ResponseEntity.ok(urlLinkService.updateUrlLink(urlLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/user/deleteurl")
    public void deleteAppUserUrlLink(@PathParam("urlId") Long urlId){
        urlLinkService.deleteUrlLink(urlId);
    }

    //=> To be tested (does not work yet)
    @DeleteMapping("/user/deleteurls")
    public void deleteAppUserUrlLinkList(@PathParam("urlIdList") List<Long> urlIdList) {
        urlLinkService.deleteUrlLinkList(urlIdList);
    }

}
